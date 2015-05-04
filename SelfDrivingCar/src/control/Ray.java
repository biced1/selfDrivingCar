package control;

import greenfoot.GreenfootImage;
import greenfootAdditions.SmoothMover;

import java.awt.Color;

public class Ray extends SmoothMover {
	private double startX;
	private double startY;
	private double maxLength = 200;
	private double currentDistance = 0;
	private double stepSize = 3;
	private boolean foundCurb = false;
	private boolean distanceReached = false;
	private int offset;

	public Ray(double xPos, double yPos, int direction, int offset) {
		this.setLocation(xPos, yPos);
		this.startX = xPos;
		this.startY = yPos;
		this.offset = offset;
		this.setRotation(direction);
		GreenfootImage image = new GreenfootImage(5, 5);
		image.setColor(new Color(0, 0, 0));
		image.fillOval(0, 0, 3, 3);
		this.setImage(image);
	}

	public void step() {
		double xChange = stepSize
				* Math.cos(this.getRotation() * Math.PI / 180);
		double yChange = stepSize
				* Math.sin(this.getRotation() * Math.PI / 180);

		this.setLocation(this.getExactX() + xChange, this.getExactY() + yChange);

		currentDistance = Math.sqrt(Math.pow(this.getExactY() - startY, 2)
				+ Math.pow(this.getExactX() - startX, 2));

		if (!this.isOnRoad()) {
			foundCurb = true;
		}
		if (currentDistance > maxLength) {
			distanceReached = true;
		}
	}

	public double getDistance() {
		return this.currentDistance;
	}

	public boolean hitCurb() {
		return this.foundCurb;
	}

	public boolean isDistanceReached() {
		return this.distanceReached;
	}

	public void reset(double xPos, double yPos, int rotation, double speed) {
		this.startX = xPos;
		this.startY = yPos;
		maxLength = speed < 2 ? 200 : speed * 50 + 100;
		this.setLocation(xPos, yPos);
		this.setRotation(rotation + offset);
		if (this.getRotation() > 359) {
			this.setRotation(this.getRotation() - 360);
		}
		foundCurb = false;
		distanceReached = false;
	}

	private boolean isOnRoad() {
		Color c = new Color(224, 230, 235);
		try {
			c = ((TestWorld2) this.getWorld()).getColor(this.getExactX(),
					this.getExactY());
		} catch (IndexOutOfBoundsException e) {

		}
		return !(c.getRed() == 224 && 230 < c.getBlue() && c.getBlue() < 240
				&& 223 < c.getGreen() && c.getGreen() < 233);
	}
	
	public int getOffset(){
		return this.offset;
	}
}
