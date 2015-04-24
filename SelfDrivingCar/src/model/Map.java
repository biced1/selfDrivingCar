package model;

import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

import java.util.List;

import control.Car;
import control.TestWorld2;

public class Map extends Actor {
	private static int scale = 4;
	private static int panSpeed = 10;
	private List<Car> cars = null;
	private long distanceFromTop = 0;
	private long distanceFromLeft = 0;

	public Map(List<Car> cars) {
		this.cars = cars;
		GreenfootImage map = new GreenfootImage(
				"images/maps/coord=40.564416,-112.304306_zoom=-2.png");
		map.scale(map.getWidth() * scale, map.getHeight() * scale);
		this.setImage(map);
	}

	@Override
	public void act() {
		if (Greenfoot.isKeyDown("up")) {
			if (distanceFromTop > 0) {
				distanceFromTop -= panSpeed;
				this.setLocation(this.getX(), this.getY() + panSpeed);
				for (Car car : cars) {
					car.getFront().setLocation(car.getFront().getX(), car.getFront().getY() + panSpeed);
					car.getRear().setLocation(car.getRear().getX(), car.getRear().getY() + panSpeed);
				}
			}
		}
		if (Greenfoot.isKeyDown("down")) {
			if (distanceFromTop < this.getImage().getHeight()
					- TestWorld2.worldHeight - panSpeed) {
				distanceFromTop += panSpeed;
				this.setLocation(this.getX(), this.getY() - panSpeed);
				for (Car car : cars) {
					car.getFront().setLocation(car.getFront().getX(), car.getFront().getY() - panSpeed);
					car.getRear().setLocation(car.getRear().getX(), car.getRear().getY() - panSpeed);
				}
			}
		}
		if (Greenfoot.isKeyDown("left")) {
			if (distanceFromLeft > 0) {
				distanceFromLeft -= panSpeed;
				this.setLocation(this.getX() + panSpeed, this.getY());
				for (Car car : cars) {
					car.getFront().setLocation(car.getFront().getX() + panSpeed, car.getFront().getY());
					car.getRear().setLocation(car.getRear().getX() + panSpeed, car.getRear().getY());
				}
			}
		}
		if (Greenfoot.isKeyDown("right")) {
			if (distanceFromLeft < this.getImage().getWidth()
					- TestWorld2.worldWidth - panSpeed) {
				distanceFromLeft += panSpeed;
				this.setLocation(this.getX() - panSpeed, this.getY());
				for (Car car : cars) {
					car.getFront().setLocation(car.getFront().getX() - panSpeed, car.getFront().getY());
					car.getRear().setLocation(car.getRear().getX() - panSpeed, car.getRear().getY());
				}
			}
		}
	}

	public long getDistanceFromTop() {
		return distanceFromTop;
	}

	public long getDistanceFromLeft() {
		return distanceFromLeft;
	}
}