package model;

import greenfoot.GreenfootImage;
import greenfootAdditions.GreenfootImageHelp;
import greenfootAdditions.SmoothMover;

public class Tile extends SmoothMover {
	private static int scale = 7;
	private String tileImagePath;
	private long xPos;
	private long yPos;
	

	public Tile(String tileImagePath, long xPos, long yPos) {
		this.tileImagePath = tileImagePath;
		this.xPos = xPos;
		this.yPos = yPos;
		setupImage();
	}

	private void setupImage() {
		GreenfootImage tileImage = new GreenfootImage(tileImagePath);
		this.setImage(GreenfootImageHelp.scale(tileImage, tileImage.getWidth() * scale, tileImage.getHeight() * scale));
	}

	public long getXPos() {
		return this.xPos;
	}

	public long getYPos() {
		return this.yPos;
	}
}
