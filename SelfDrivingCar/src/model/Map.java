package model;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfootAdditions.SmoothMover;

import java.util.List;

import control.Car;

public class Map extends SmoothMover {
	private static int scale = 4;
	private static int panSpeed = 10;
	private long distanceFromTop = 0;
	private long distanceFromLeft = 0;
	private static int noChange = 0;

	public Map(List<Car> cars) {
		setupImage();
	}

	@Override
	public void act() {
		if (Greenfoot.isKeyDown("up")) {
			pan(noChange, panSpeed);
		}
		if (Greenfoot.isKeyDown("down")) {
			pan(noChange, -panSpeed);
		}
		if (Greenfoot.isKeyDown("left")) {
			pan(panSpeed, noChange);
		}
		if (Greenfoot.isKeyDown("right")) {
			pan(-panSpeed, noChange);
		}
		move();
	}

	private void setupImage() {
		GreenfootImage map = new GreenfootImage("images/maps/coord=40.564416,-112.304306_zoom=-2.png");
		map.scale(map.getWidth() * scale, map.getHeight() * scale);
		this.setImage(map);
	}

	private void pan(int x, int y) {
		distanceFromLeft -= x;
		distanceFromTop -= y;
		this.setLocation(this.getExactX() + x, this.getExactY() + y);
	}

	public long getDistanceFromTop() {
		return distanceFromTop;
	}

	public long getDistanceFromLeft() {
		return distanceFromLeft;
	}
}