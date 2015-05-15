package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Point;
import model.Tile;
import view.SetupInput;

public class MapIO {
	private static int zoom = 19;
	private static int halfCircle = 180;
	private static int circle = 360;

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
		Point topLeft = getTileNumber(startLatitude, startLongitude, zoom);
		Point bottomRight = getTileNumber(endLatitude, endLongitude, zoom);
		
		for(long x = topLeft.getX(); x < bottomRight.getX(); x++){
			for(long y = bottomRight.getY(); y < topLeft.getY(); y++){
				String path = zoom + "/" + x + "/" + y + ".png";
				tiles.add(new Tile(path, x - topLeft.getX(), y - bottomRight.getY()));
			}
		}		
		return tiles;
	}
	
	 private Point getTileNumber(final double lat, final double lon, final int zoom) {
		   int xtile = (int)Math.floor( (lon + halfCircle) / circle * (1<<zoom)	  ) ;
		   int ytile = (int)Math.floor( (1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2 * (1<<zoom) ) ;
		    if (xtile < 0)
		     xtile=0;
		    if (xtile >= (1<<zoom))
		     xtile=((1<<zoom)-1);
		    if (ytile < 0)
		     ytile=0;
		    if (ytile >= (1<<zoom))
		     ytile=((1<<zoom)-1);
		    return new Point(xtile, ytile);
		   }
}
