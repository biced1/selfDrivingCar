package control;

import greenfootAdditions.SmoothMover;
import greenfootAdditions.Vector;
import model.Map;

public class Car extends SmoothMover {
	private final double maxSpeed = 5;
	private Map gps;
	private Tire front = new FrontTire();
	private Tire rear = new BackTire();

	public Car() {
		super();
		setRed();
		setBlue();
	}

	public void setMap(Map map) {
		this.gps = map;
	}

	private void setPosition() {
		this.setLocation((front.getExactX() + rear.getExactX()) / 2, (front.getExactY() + rear.getExactY()) / 2);
		this.setRotation(rear.getRotation());
	}

	@Override
	public void act() {
		setPosition();
		calculateCorners();
		if (this.isTouching(Map.class) && this.isOnRoad()) {
			this.setBlue();
		} else {
			this.setRed();
		}
		// if (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("A")) {
		// turnLeft();
		// }
		// if (Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("D")) {
		// turnRight();
		// }
		// if (Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("S")) {
		// reverse();
		// }
		// if (Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("W")) {
		// accelerate();
		// }
		// if (Greenfoot.isKeyDown("space")) {
		// brake();
		// }
		// adjustSpeed();
		// move();
	}

	private void setBlue() {
		this.setImage("images/regular/blueCar.png");
	}

	private void setRed() {
		this.setImage("images/regular/redCar.png");
	}

	// private void setYellow() {
	// this.setImage("images/regular/yellowCar.png");
	// }

	private void turnLeft() {
		int reverseRotation = this.getRotation() > 179 ? this.getRotation() - 180 : this.getRotation() + 180;

		int actualRotation = this.getMovement().getDirection() % 360;
		if (actualRotation < 0) {
			actualRotation += 360;
		}

		if (this.getSpeed() > .4) {
			if (actualRotation == reverseRotation) {
				this.getMovement().setDirection(this.getMovement().getDirection() + 1);
				this.setRotation(this.getRotation() + 1);
			} else {
				this.getMovement().setDirection(this.getMovement().getDirection() - 1);
				this.setRotation(this.getRotation() - 1);
			}
		}
	}

	private void turnRight() {
		int reverseRotation = this.getRotation() > 179 ? this.getRotation() - 180 : this.getRotation() + 180;

		int actualRotation = this.getMovement().getDirection() % 360;
		if (actualRotation < 0) {
			actualRotation += 360;
		}

		if (this.getSpeed() > .4) {
			if (actualRotation == reverseRotation) {
				this.getMovement().setDirection(this.getMovement().getDirection() - 1);
				this.setRotation(this.getRotation() - 1);
			} else {
				this.getMovement().setDirection(this.getMovement().getDirection() + 1);
				this.setRotation(this.getRotation() + 1);
			}
		}
	}

	private void accelerate() {
		double delta = .04;
		int actualRotation = this.getMovement().getDirection() % 360;
		if (actualRotation < 0) {
			actualRotation += 360;
		}

		if (this.getSpeed() == 0) {
			this.addForce(new Vector(this.getRotation(), .6));
		} else if (actualRotation == this.getRotation() && this.getSpeed() < maxSpeed) {
			this.accelerate(1 + delta);
		} else {
			this.accelerate(1 - delta);
		}
	}

	private void reverse() {
		double delta = .05;
		int reverseRotation = this.getRotation() > 179 ? this.getRotation() - 180 : this.getRotation() + 180;

		int actualRotation = this.getMovement().getDirection() % 360;
		if (actualRotation < 0) {
			actualRotation += 360;
		}

		if (this.getSpeed() == 0) {
			this.addForce(new Vector(reverseRotation, .6));
		} else if (actualRotation == reverseRotation && this.getSpeed() < maxSpeed) {
			this.accelerate(1 + delta);
		} else {
			this.accelerate(1 - delta);
		}
	}

	private void brake() {
		this.accelerate(.96);
	}

	private void adjustSpeed() {
		this.accelerate(.99);
	}

	private void calculateCorners() {
		// backRight.
	}

	private boolean isOnRoad() {
		return true;
		// return (c.getRed() == 255 && c.getBlue() == 255 && c.getGreen() ==
		// 255);
	}

	public Tire getFront() {
		return front;
	}

	public Tire getRear() {
		return rear;
	}
}
