package control;

import greenfoot.GreenfootImage;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import model.Tile;

public class TileManager {
	private List<Tile> allTiles;
	private List<Tile> currentTiles = new ArrayList<Tile>();
	private int row = 15;

	public TileManager(List<Tile> tiles) {
		this.allTiles = tiles;
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
}
