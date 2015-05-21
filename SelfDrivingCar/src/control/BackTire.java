package control;

import greenfoot.GreenfootImage;
import greenfootAdditions.SmoothMover;
import greenfootAdditions.Vector;

import java.awt.Color;

public class BackTire extends SmoothMover {
	private static int carLength;
	private FrontTire frontTire;
	private int tireSize = 2;
	private int gray = 88;
	private int squared = 2;

	public BackTire(int carLength) {
		GreenfootImage tire = new GreenfootImage(tireSize, tireSize);
		tire.setColor(new Color(gray, gray, gray));
		tire.fill();
		this.setImage(tire);
		BackTire.carLength = carLength;
	}

	@Override
	public void act() {
		turnTowardsFront();
		setMovementVector();
		move();
	}

	private void setMovementVector() {
		double distanceBetween = getDistanceBetweenTires();
		if (distanceBetween > carLength) {
			this.getMovement().setNeutral();
			this.addForce(new Vector(this.getRotation(), frontTire.getSpeed()
					+ distanceBetween - carLength));
		} else if (distanceBetween < carLength) {
			this.getMovement().setNeutral();
			this.addForce(new Vector(this.getRotation(), frontTire.getSpeed()
					- (carLength - distanceBetween)));
		}
	}

	private void turnTowardsFront() {
		this.turnTowards(frontTire.getX(), frontTire.getY());
	}

	public void setFrontTire(FrontTire frontTire) {
		this.frontTire = frontTire;
	}

	private double getDistanceBetweenTires() {
		return Math.sqrt(Math.pow(frontTire.getExactY() - this.getExactY(), squared)
				+ Math.pow(frontTire.getExactX() - this.getExactX(), squared));
	}
	
	public static int getCarLength() {
		return carLength;
	}

	public static void setCarLength(int carLength) {
		BackTire.carLength = carLength;
	}
}
