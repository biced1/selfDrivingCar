package control;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import model.Coordinate;
import model.Directions;
import model.Step;
import model.StepCommand;
import model.Tile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DirectionsManager {

	private String connectionString = "https://maps.googleapis.com/maps/api/directions/json?";
	private String apiKey = "AIzaSyCwDvRNgv1xTSLz83PE1sJYF79MDjyF0BI";
	private TileManager tileManager;
	private int longFeetAdjust = 11;
	private int latFeetAdjust = 5;

	private final int feetPerMile = 5280;

	public DirectionsManager(TileManager tileManager) {
		this.tileManager = tileManager;
	}

	public Directions getDirections(JSONObject JSONSteps) {
		JSONArray stepsArray = null;
		try {
			stepsArray = JSONSteps.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Step> steps = new ArrayList<Step>();
		for (int step = 0; step < stepsArray.length(); step++) {
			JSONObject currentStep = null;
			try {
				currentStep = stepsArray.getJSONObject(step);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			double startLatitude = 0;
			double startLongitude = 0;
			double endLatitude = 0;
			double endLongitude = 0;
			try {
				startLatitude = currentStep.getJSONObject("start_location").getDouble("lat");
				startLongitude = currentStep.getJSONObject("start_location").getDouble("lng");
				endLatitude = currentStep.getJSONObject("end_location").getDouble("lat");
				endLongitude = currentStep.getJSONObject("end_location").getDouble("lng");
			} catch (JSONException e) {
				e.printStackTrace();
			}

			Coordinate startCoord = new Coordinate(startLatitude, startLongitude);
			Coordinate endCoord = new Coordinate(endLatitude, endLongitude);
			String instructions = "";
			try {
				instructions = currentStep.getString("html_instructions");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			StepCommand stepCommand = StepCommand.EAST;
			if (step == 0) {
				if (instructions.contains("<b>east</b>")) {
					stepCommand = StepCommand.EAST;
				} else if (instructions.contains("<b>west</b>")) {
					stepCommand = StepCommand.WEST;
				} else if (instructions.contains("<b>north</b>")) {
					stepCommand = StepCommand.NORTH;
				} else if (instructions.contains("<b>south</b>")) {
					stepCommand = StepCommand.SOUTH;
				}
			} else {
				if (instructions.contains("<b>right</b>")) {
					stepCommand = StepCommand.RIGHT;
				} else if (instructions.contains("<b>left</b>")) {
					stepCommand = StepCommand.LEFT;
				}
			}

			steps.add(new Step(startCoord, endCoord, stepCommand, instructions));
		}
		return new Directions(steps);
	}

	public JSONObject getDirections(double startLongitude, double startLatitude, double endLongitude, double endLatitude) {
		String connectionParameters = "origin=" + startLatitude + "," + startLongitude + "&destination=" + endLatitude + "," + endLongitude + "&key=" + apiKey;
		connectionString += connectionParameters;
		String directionsResponse = executePost(connectionString, "");
		JSONObject directionsJSON = null;
		try {
			directionsJSON = new JSONObject(directionsResponse);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return directionsJSON;
	}

	public Coordinate getCoordinateAt(double exactX, double exactY) {
		int tilesAway = 1;
		Tile t = tileManager.getTileAt((int) exactX, (int) exactY);
		double topLatitude = TileManager.getLatitudeAtTile(t.getYPos() + t.getyPosOffset());
		double bottomLatitude = TileManager.getLatitudeAtTile(t.getYPos() + tilesAway + t.getyPosOffset());
		double leftLongitude = TileManager.getLongitudeAtTile(t.getXPos() + t.getxPosOffset());
		double rightLongitude = TileManager.getLongitudeAtTile(t.getXPos() + tilesAway + t.getxPosOffset());		
		
		double tileXPos = exactX % t.getImage().getWidth();
		double tileYPos = exactY % t.getImage().getHeight();

		double exactLatitude = getValueBetween(topLatitude, bottomLatitude, tileYPos / t.getImage().getHeight());
		double exactLongitude = getValueBetween(leftLongitude, rightLongitude, tileXPos / t.getImage().getWidth());
		
		double tileWidth = calculateDistance(topLatitude, leftLongitude, topLatitude, rightLongitude) * feetPerMile;
		double tileHeight = calculateDistance(topLatitude, leftLongitude, bottomLatitude, leftLongitude) * feetPerMile;
		double longAdjustPercent = longFeetAdjust / tileWidth;
		double latAdjustPercent = latFeetAdjust / tileHeight;
		
		double longitudeAdjust = (rightLongitude - leftLongitude) * longAdjustPercent;
		double latitudeAdjust = (bottomLatitude - topLatitude) * latAdjustPercent;
		
		exactLongitude += longitudeAdjust;
		exactLatitude += latitudeAdjust;
//		System.out.println(latAdjustPercent + " " + latitudeAdjust);

		return new Coordinate(exactLatitude, exactLongitude);
	}

	private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(degreesToRadians(lat1)) * Math.sin(degreesToRadians(lat2)) + Math.cos(degreesToRadians(lat1)) * Math.cos(degreesToRadians(lat2))
				* Math.cos(degreesToRadians(theta));
		dist = Math.acos(dist);
		dist = radiansToDegrees(dist);
		dist = dist * 60 * 1.1515;
		return (dist);
	}

	private double radiansToDegrees(double rad) {
		return (rad * 180 / Math.PI);
	}

	private double degreesToRadians(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private double getValueBetween(double first, double second, double percentage) {
		double difference = second - first;
		return first + (difference * percentage);
	}

	public static String executePost(String targetURL, String urlParameters) {
		HttpURLConnection connection = null;
		try {
			connection = createConnection(targetURL, urlParameters);

			sendRequest(connection, urlParameters);

			StringBuilder response = getResponse(connection);
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	private static HttpURLConnection createConnection(String targetURL, String urlParameters) throws IOException {

		HttpURLConnection connection = null;
		URL url = new URL(targetURL);
		connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
		connection.setRequestProperty("Content-Language", "en-US");

		connection.setUseCaches(false);
		connection.setDoOutput(true);
		return connection;
	}

	private static void sendRequest(HttpURLConnection connection, String urlParameters) throws IOException {
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.close();
	}

	private static StringBuilder getResponse(HttpURLConnection connection) throws IOException {
		InputStream is = connection.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		StringBuilder response = new StringBuilder(); // or StringBuffer if
														// not Java 5+
		String line;
		while ((line = rd.readLine()) != null) {
			response.append(line);
			response.append('\r');
		}
		rd.close();
		return response;
	}
}
