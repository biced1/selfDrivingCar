package control;

import model.Curb;
import greenfoot.Actor;
import greenfoot.Greenfoot;

public class Car extends Actor {
	private int speed = 0;
	private int counter = 0;
	private int turnSpeed = 0;
	private final int stopped = 0;
	private final int brakes = 5;
	private final int counterTime = 15;
	private final int topSpeed = 4;
	private final int topTurnSpeed = 4;

	public Car() {
		super();
		setRed();
		setBlue();

	}

	@Override
	public void act() {
		boolean keyDown = false;
		setRules();
		if (this.isTouching(Curb.class)) {
			setRed();
		} else {
			setBlue();
		}

		if (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("A")) {
			turnLeft();
		}
		if (Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("D")) {
			turnRight();
		}
		if (Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("S")) {
			reverse();
			keyDown = true;
		}
		if (Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("W")) {
			accelerate();
			keyDown = true;
		}
		if (Greenfoot.isKeyDown("space")) {
			brake();
		}
		if (!keyDown)
			adjustSpeed();
		move();
	}

	private void setBlue() {
		this.setImage("images/regular/blueCar.png");
	}

	private void setRed() {
		this.setImage("images/regular/redCar.png");
	}

	private void setRules() {
		turnSpeed = speed <= 2 && speed >= -2 ? speed : 8 / speed;
		if (counter == counterTime)
			counter = 0;
		counter++;
	}

	private void turnLeft() {
		if (turnSpeed > topTurnSpeed)
			speed--;
		this.turn(-turnSpeed);
	}

	private void turnRight() {
		if (turnSpeed > topTurnSpeed)
			speed--;
		this.turn(turnSpeed);
	}

	private void accelerate() {
		if (speed < topSpeed && counter % brakes + 1 == brakes)
			speed++;
	}

	private void reverse() {
		if (speed > -topSpeed && counter % brakes + 1 == brakes)
			speed--;
	}

	private void brake() {
		if (speed > stopped && counter % brakes + 1 == brakes) {
			speed--;
		} else if (speed < stopped && counter % brakes + 1 == brakes) {
			speed++;
		}
	}

	private void adjustSpeed() {
		if (speed > stopped && counter == counterTime)
			speed--;
		else if (speed < stopped && counter == counterTime)
			speed++;
	}

	private void move() {
		this.move(speed);
	}
}
