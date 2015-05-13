package control;

import java.io.IOException;

import view.SetupInput;

public class MapIO {
	private static int zoom = 18;
	
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
					"perl Geo-OSM-Tiles-0.04/downloadosmtiles.pl --lat=" + startLatitude + ":" + endLatitude + " --lon=" + startLongitude + ":" + endLongitude + " --zoom=" + zoom);
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
	
	
}
