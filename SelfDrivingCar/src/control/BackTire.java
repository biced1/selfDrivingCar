package control;

import greenfoot.GreenfootImage;
import greenfootAdditions.SmoothMover;
import greenfootAdditions.Vector;

import java.awt.Color;
import java.util.List;

public class BackTire extends SmoothMover {
	private static int carLength = 44;
	private FrontTire frontTire;

	public BackTire() {
		GreenfootImage tire = new GreenfootImage(10, 10);
		tire.setColor(new Color(84, 84, 84));
		tire.fill();
		this.setImage(tire);
	}

	@Override
	public void act() {
		setFrontTire();
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

	private void setFrontTire() {
		List<FrontTire> frontTires = this.getObjectsInRange(110,
				FrontTire.class);
		this.frontTire = frontTires.get(0);
	}

	private double getDistanceBetweenTires() {
		return Math.sqrt(Math.pow(frontTire.getExactY() - this.getExactY(), 2)
				+ Math.pow(frontTire.getExactX() - this.getExactX(), 2));
	}
}
