package model;

import greenfoot.GreenfootImage;
import greenfootAdditions.SmoothMover;

public class Tile extends SmoothMover{
	//private static int scale = 4;
	private String tileImagePath;
	
	public Tile(String tileImagePath){
		this.tileImagePath = tileImagePath;
		setupImage();
	}
	
	private void setupImage() {
		GreenfootImage tileImage = new GreenfootImage(tileImagePath);
		//tileImage.scale(tileImage.getWidth() * scale, tileImage.getHeight() * scale);
		this.setImage(tileImage);
	}
}
