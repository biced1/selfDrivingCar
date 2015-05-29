package control;

import greenfoot.GreenfootImage;
import greenfootAdditions.SmoothMover;

import java.awt.Color;

public class Ray extends SmoothMover {
	private double startX;
	private double startY;

	private double rayLength = 100;
	private double minLength = 100;

	private double currentDistance = 0;
	private double stepSize = 1;
	private boolean foundCurb = false;
	private boolean distanceReached = false;
	private int offset;
	private int size = 3;
	private int middle = 0;

	private int redValue = 0;
	private int greenValue = 0;
	private int blueValue = 0;

	private int circle = 360;
	private int halfCircle = 180;
	private int squared = 2;
	private GreenfootImage image = new GreenfootImage(size, size);

	public Ray(double xPos, double yPos, int direction, int offset) {
		this.setLocation(xPos, yPos);
		this.startX = xPos;
		this.startY = yPos;
		this.offset = offset;
		this.setRotation(direction);
		image.setColor(new Color(redValue, greenValue, blueValue));
		image.fillOval(middle, middle, size, size);
		this.setImage(image);
	}

	public void step() {
		double xChange = stepSize * getXComponent(this.getRotation());
		double yChange = stepSize * getYComponent(this.getRotation());

		this.setLocation(this.getExactX() + xChange, this.getExactY() + yChange);

		currentDistance = getDistance(this.getExactX(), this.getExactY(),
				startX, startY);

		if (!this.isOnRoad()) {
			foundCurb = true;
		}
		if (currentDistance > rayLength) {
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
		int lookaheadConstant = 1;

		this.startX = xPos;
		this.startY = yPos;

		rayLength = speed * lookaheadConstant;
		if (rayLength < minLength) {
			rayLength = minLength;
		}
		this.setLocation(xPos, yPos);
		this.setRotation(rotation + offset);
		if (this.getRotation() >= circle) {
			this.setRotation(this.getRotation() - circle);
		}
		foundCurb = false;
		distanceReached = false;
	}

	private boolean isOnRoad() {
			Color testColor = ((TestWorld2) this.getWorld()).getColor(
					this.getExactX(), this.getExactY());
		return ColorValidation.isRoadColor(testColor);
	}

	public int getOffset() {
		return this.offset;
	}

	public void setColor(Color c) {
		image.setColor(c);
		image.fillOval(middle, middle, size, size);
	}

	private double getDistance(double firstX, double firstY, double secondX,
			double secondY) {
		return Math.sqrt(Math.pow(firstY - secondY, squared)
				+ Math.pow(firstX - secondX, squared));
	}

	private double getXComponent(int degrees) {
		return Math.cos(degrees * Math.PI / halfCircle);
	}

	private double getYComponent(int degrees) {
		return Math.sin(degrees * Math.PI / halfCircle);
	}
}
