package control;

import greenfootAdditions.SmoothMover;
import greenfootAdditions.Vector;
import model.Map;

public class Car extends SmoothMover {
	private final double maxSpeed = 5;
	private SmoothMover front = new FrontTire();
	private SmoothMover rear = new BackTire();

	public Car() {
		super();
		setRed();
		setBlue();
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
	
	private void calculateCorners() {
		// backRight.
	}

	private boolean isOnRoad() {
		return true;
		// return (c.getRed() == 255 && c.getBlue() == 255 && c.getGreen() ==
		// 255);
	}

	public SmoothMover getFront() {
		return front;
	}

	public SmoothMover getRear() {
		return rear;
	}
}
