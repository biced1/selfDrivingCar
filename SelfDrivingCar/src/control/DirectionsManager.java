package control;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import model.Coordinate;
import model.Tile;

import org.json.JSONException;
import org.json.JSONObject;

public class DirectionsManager {

	private String connectionString = "https://maps.googleapis.com/maps/api/directions/json?";
	private String apiKey = "AIzaSyCwDvRNgv1xTSLz83PE1sJYF79MDjyF0BI";
	private TileManager tileManager;
	
	public DirectionsManager(TileManager tileManager){
		this.tileManager = tileManager;
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
	
	public Coordinate getCoordinateAt(double exactX, double exactY){
		int tilesAway = 1;
		Tile t = tileManager.getTileAt((int)exactX, (int)exactY);
		double topLatitude = TileManager.getLatitudeAtTile(t.getYPos() + t.getyPosOffset());
		double bottomLatitude = TileManager.getLatitudeAtTile(t.getYPos() + tilesAway + t.getyPosOffset());
		double leftLongitude = TileManager.getLongitudeAtTile(t.getXPos() + t.getxPosOffset());
		double rightLongitude = TileManager.getLongitudeAtTile(t.getXPos() + tilesAway + t.getxPosOffset());
		
		double tileXPos = exactX % t.getImage().getWidth();
		double tileYPos = exactY % t.getImage().getHeight();
		
		double exactLatitude = getValueBetween(topLatitude, bottomLatitude, tileYPos / t.getImage().getHeight());
		double exactLongitude = getValueBetween(leftLongitude, rightLongitude, tileXPos / t.getImage().getWidth());
		
		return new Coordinate(exactLatitude, exactLongitude);
	}
	
	private double getValueBetween(double first, double second, double percentage){
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
	
	private static HttpURLConnection createConnection(String targetURL, String urlParameters) throws IOException{

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
	
	private static void sendRequest(HttpURLConnection connection, String urlParameters) throws IOException{
		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.close();
	}
	
	private static StringBuilder getResponse(HttpURLConnection connection) throws IOException{
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
