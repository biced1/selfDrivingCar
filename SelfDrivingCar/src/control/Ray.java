package control;

import greenfoot.GreenfootImage;
import greenfootAdditions.SmoothMover;

import java.awt.Color;

public class Ray extends SmoothMover {
	private double startX;
	private double startY;

	private double maxLength = 200;
	private double minLength = 200;

	private double currentDistance = 0;
	private double stepSize = 5;
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
		int lookaheadConstant = 50;

		this.startX = xPos;
		this.startY = yPos;

		maxLength = speed * lookaheadConstant;
		if (maxLength < minLength) {
			maxLength = minLength;
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
		int invalidRedValue = 224;
		int redErrorThreshold = 0;
		int invalidBlueValue = 235;
		int blueErrorThreshold = 5;
		int invalidGreenValue = 228;
		int greenErrorThreshold = 5;

		Color testColor = new Color(invalidRedValue, invalidGreenValue,
				invalidBlueValue);
		try {
			testColor = ((TestWorld2) this.getWorld()).getColor(
					this.getExactX(), this.getExactY());
		} catch (IndexOutOfBoundsException e) {

		}
		return !(isWithinThreshold(testColor.getRed(), invalidRedValue,
				redErrorThreshold)
				&& isWithinThreshold(testColor.getBlue(), invalidBlueValue,
						blueErrorThreshold) && isWithinThreshold(
					testColor.getGreen(), invalidGreenValue,
					greenErrorThreshold));
	}

	private boolean isWithinThreshold(int testValue, int prefferedValue,
			int threshold) {
		return prefferedValue - threshold <= testValue
				&& testValue <= prefferedValue + threshold;
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
