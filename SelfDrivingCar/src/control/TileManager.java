package control;

import greenfoot.GreenfootImage;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import model.Point;
import model.Tile;

public class TileManager {
	private List<Tile> allTiles;
	private List<Tile> currentTiles = new ArrayList<Tile>();
	private int row = 15;
	private static int circle = 360;
	private static int halfCircle = 180;

	public TileManager(List<Tile> tiles) {
		this.allTiles = tiles;
	}
	
	public void scaleTiles(double scaleModifier){
		Tile.setScale(Tile.getBaseScale() * scaleModifier);
		for(Tile t : allTiles){
			t.scaleImage();
		}
	}

	public Tile getTileAt(int xPos, int yPos) {
		Tile first = allTiles.get(0);
		int desiredXPos = xPos / first.getImage().getWidth();
		int desiredYPos = yPos / first.getImage().getHeight();
		
		Tile tile = null;
		int index = desiredXPos * row + desiredYPos;
		if (index >= 0 && index < allTiles.size()) {
			tile = allTiles.get(index);
		}
		return tile;
	}

	public Color getColorAt(int xPos, int yPos) {
		Tile t = getTileAt(xPos, yPos);
		GreenfootImage tileImage = t.getImage();
		int xPositionOnTile = xPos % tileImage.getWidth();
		int yPositionOnTile = yPos % tileImage.getWidth();

		return tileImage.getColorAt(xPositionOnTile, yPositionOnTile);
	}

	public List<Tile> getTileSection(int xPos, int yPos) {
		Tile first = allTiles.get(0);
		int desiredXPos = xPos / first.getImage().getWidth();
		int desiredYPos = yPos / first.getImage().getHeight();

		int viewRange = 1;

		for (int x = -viewRange; x <= viewRange; x++) {
			for (int y = -viewRange; y <= viewRange; y++) {
				Tile toAdd = getTileByIndex(desiredXPos + x, desiredYPos + y);
				if (toAdd != null)
					currentTiles.add(toAdd);
			}
		}
		return currentTiles;
	}

	public List<Tile> getCurrentTiles() {
		return currentTiles;
	}
	
	public void clearCurrentTiles() {
		currentTiles.clear();
	}

	private Tile getTileByIndex(int xIndex, int yIndex) {
		Tile tile = null;
		int index = xIndex * row + yIndex;
		if (index >= 0 && index < allTiles.size()) {
			tile = allTiles.get(index);
		}
		return tile;
	}
	
	public static double getLongitudeAtTile(long x){
		return tileToLongitude(x, MapIO.getZoomLevel());
	}
	
	public static double getLatitudeAtTile(long y){
		return tileToLatitude(y, MapIO.getZoomLevel());
	}
	
	public static Point getTileNumber(double lat, double lon){
		return getTileNumber(lat, lon, MapIO.getZoomLevel());
	}

	public static Point getTileNumber(double lat, double lon, final int zoom) {
		int bitShiftAmount = 1;
		int xtile = (int) Math.floor((lon + halfCircle) / circle * (bitShiftAmount << zoom));
		int ytile = (int) Math.floor((1 - Math.log(Math.tan(Math.toRadians(lat)) + 1 / Math.cos(Math.toRadians(lat))) / Math.PI) / 2 * (1 << zoom));
		if (xtile < 0)
			xtile = 0;
		if (xtile >= (bitShiftAmount << zoom))
			xtile = ((bitShiftAmount << zoom) - bitShiftAmount);
		if (ytile < 0)
			ytile = 0;
		if (ytile >= (bitShiftAmount << zoom))
			ytile = ((bitShiftAmount << zoom) - bitShiftAmount);
		return new Point(xtile, ytile);
	}

	public static double tileToLongitude(long x, long z) {
		return x / Math.pow(2.0, z) * circle - halfCircle;
	}

	public static double tileToLatitude(long y, long z) {
		double n = Math.PI - (2.0 * Math.PI * y) / Math.pow(2.0, z);
		return Math.toDegrees(Math.atan(Math.sinh(n)));
	}
}
