package model;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfootAdditions.SmoothMover;

import java.util.List;

import control.Car;
import control.TestWorld2;

public class Map extends SmoothMover {
	private static int scale = 4;
	private static int panSpeed = 10;
	private List<Car> cars;
	private long distanceFromTop = 0;
	private long distanceFromLeft = 0;

	public Map(List<Car> cars) {
		this.cars = cars;
		setupImage();
	}

	@Override
	public void act() {
//		if (Greenfoot.isKeyDown("up")) {
//			if (distanceFromTop > 0) {
//				pan(0, panSpeed);
//			}
//		}
//		if (Greenfoot.isKeyDown("down")) {
//			if (distanceFromTop < this.getImage().getHeight() - TestWorld2.worldHeight - panSpeed) {
//				pan(0, -panSpeed);
//			}
//		}
//		if (Greenfoot.isKeyDown("left")) {
//			if (distanceFromLeft > 0) {
//				pan(panSpeed, 0);
//			}
//		}
//		if (Greenfoot.isKeyDown("right")) {
//			if (distanceFromLeft < this.getImage().getWidth() - TestWorld2.worldWidth - panSpeed) {
//				pan(-panSpeed, 0);
//			}
//		}
		move();
	}
	
	private void setupImage(){
		GreenfootImage map = new GreenfootImage("images/maps/coord=40.564416,-112.304306_zoom=-2.png");
		map.scale(map.getWidth() * scale, map.getHeight() * scale);
		this.setImage(map);
	}

	private void pan(int x, int y) {
		distanceFromLeft -= x;
		distanceFromTop -= y;
		this.setLocation(this.getExactX() + x, this.getExactY() + y);
		for (Car car : cars) {
			car.getFront().setLocation(car.getFront().getExactX() + x, car.getFront().getExactY() + y);
			car.getRear().setLocation(car.getRear().getExactX() + x, car.getRear().getExactY() + y);
		}
	}

	public long getDistanceFromTop() {
		return distanceFromTop;
	}

	public long getDistanceFromLeft() {
		return distanceFromLeft;
	}
}