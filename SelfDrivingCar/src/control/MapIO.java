package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Coordinate;
import model.Point;
import model.Tile;
import view.SetupInput;

public class MapIO {
	private static int zoom = 19;

	public void downloadMaps(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
		if (startLatitude > endLatitude) {
			double temp = startLatitude;
			startLatitude = endLatitude;
			endLatitude = temp;
		}
		if (startLongitude > endLongitude) {
			double temp = startLongitude;
			startLongitude = endLongitude;
			endLongitude = temp;
		}
		Process perlMapDownloader;
		try {
			perlMapDownloader = Runtime.getRuntime().exec(
					"perl Geo-OSM-Tiles-0.04/downloadosmtiles.pl --lat=" + startLatitude + ":" + endLatitude + " --lon=" + startLongitude + ":" + endLongitude
							+ " --zoom=" + zoom);
			SetupInput.displayDownloading();
			perlMapDownloader.waitFor();
			if (perlMapDownloader.exitValue() == 0) {
				System.out.println("Command Successful");
			} else {
				System.out.println("Command Failure");
			}
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public List<Tile> getTiles(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
		List<Tile> tiles = new ArrayList<Tile>();
		Point topLeft = TileManager.getTileNumber(startLatitude, startLongitude, zoom);
		Point bottomRight = TileManager.getTileNumber(endLatitude, endLongitude, zoom);

		for (long x = topLeft.getX(); x < bottomRight.getX(); x++) {
			for (long y = bottomRight.getY(); y < topLeft.getY(); y++) {
				String path = zoom + "/" + x + "/" + y + ".png";
				double longitude = TileManager.tileToLongitude(x, zoom);
				double latitude = TileManager.tileToLatitude(y, zoom);
				Coordinate tileCoordinate = new Coordinate(latitude, longitude);
				tiles.add(new Tile(path, x - topLeft.getX(), y - bottomRight.getY(), tileCoordinate, topLeft.getX(), bottomRight.getY()));
			}
		}
		return tiles;
	}

	public static int getZoomLevel() {
		return zoom;
	}
}
