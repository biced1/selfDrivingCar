package model;

import greenfoot.GreenfootImage;
import greenfootAdditions.GreenfootImageHelp;
import greenfootAdditions.SmoothMover;

public class Tile extends SmoothMover {
	private final static double baseScale = 1;
	private static double scale = baseScale;
	private String tileImagePath;
	private long xPos;
	private long yPos;
	private final long xPosOffset;
	private final long yPosOffset;
	private Coordinate topLeftCoordinate;
	

	public Tile(String tileImagePath, long xPos, long yPos, Coordinate coord, long xPosOffset, long yPosOffset) {
		this.tileImagePath = tileImagePath;
		this.xPos = xPos;
		this.yPos = yPos;
		this.setTopLeftCoordinate(coord);
		this.xPosOffset = xPosOffset;
		this.yPosOffset = yPosOffset;	
		setupImage();
	}

	private void setupImage() {
		GreenfootImage tileImage = new GreenfootImage(tileImagePath);
		this.setImage(GreenfootImageHelp.scale(tileImage, tileImage.getWidth() * (int)scale, tileImage.getHeight() * (int)scale));
	}
	
	public void scaleImage(){
		GreenfootImage tileImage = new GreenfootImage(tileImagePath);
		this.setImage(GreenfootImageHelp.scale(tileImage, (int)(tileImage.getWidth() * scale), (int)(tileImage.getHeight() * scale)));
	}

	public static double getScale() {
		return scale;
	}

	public static void setScale(double scale) {
		Tile.scale = scale;
	}

	public static double getBaseScale() {
		return baseScale;
	}

	public long getXPos() {
		return this.xPos;
	}

	public long getYPos() {
		return this.yPos;
	}

	public Coordinate getTopLeftCoordinate() {
		return topLeftCoordinate;
	}

	public void setTopLeftCoordinate(Coordinate topLeftCoordinate) {
		this.topLeftCoordinate = topLeftCoordinate;
	}

	public long getxPosOffset() {
		return xPosOffset;
	}

	public long getyPosOffset() {
		return yPosOffset;
	}
}
