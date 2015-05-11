package model;

import greenfoot.GreenfootImage;
import greenfootAdditions.SmoothMover;

import java.util.List;

import control.Car;

public class Map extends SmoothMover {
	private static int scale = 4;
	private long distanceFromTop = 0;
	private long distanceFromLeft = 0;
	private String mapImagePath = "images/maps/coord=40.564416,-112.304306_zoom=-2.png";

	public Map(List<Car> cars) {
		setupImage();
	}

	@Override
	public void act() {
		move();
	}

	private void setupImage() {
		GreenfootImage map = new GreenfootImage(mapImagePath);
		map.scale(map.getWidth() * scale, map.getHeight() * scale);
		this.setImage(map);
	}

	public long getDistanceFromTop() {
		return distanceFromTop;
	}

	public long getDistanceFromLeft() {
		return distanceFromLeft;
	}
}