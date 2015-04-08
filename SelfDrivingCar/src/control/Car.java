package control;

import greenfoot.Actor;
import greenfoot.Greenfoot;

public class Car extends Actor {
	private int speed = 0;
	private int counter = 0;
	private int turnSpeed = 0;
	private boolean turning = false;
	private final int stopped = 0;
	private final int brakes = 2;
	private final int counterTime = 10;
	private final int topSpeed = 8;
	private final int topTurnSpeed = 5;

	public Car() {
		super();
		this.setImage("images/regular/blueCar.png");
		System.out.println(this.getImage().getHeight());
		System.out.println(this.getImage().getWidth());
		this.getImage().scale(this.getImage().getWidth() / 2,
				this.getImage().getHeight() / 2);

	}

	@Override
	public void act() {
		turnSpeed = speed <= 2 && speed >= -2 ? speed : 8 / speed;
		turning = false;
		System.out.println(speed);
		if (counter == counterTime)
			counter = 0;
		counter++;
		if (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("A")) {
			if (turnSpeed > topTurnSpeed)
				speed--;
			this.turn(-turnSpeed);
			turning = true;
		}
		if (Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("D")) {
			if (turnSpeed > topTurnSpeed)
				speed--;
			this.turn(turnSpeed);
			turning = true;
		}
		if (Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("S")) {
			if (turning) {
				if (counter == counterTime)
					speed--;
			} else if (speed > stopped && counter % 3 == brakes)
				speed--;
			else if (speed > -topSpeed)
				speed--;
		}
		if (Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("W")) {
			if (turning) {
				if (counter == counterTime)
				speed++;
			} else if (speed < stopped && counter % 3 == brakes)
				speed++;
			else if (speed < topSpeed)
				speed++;
		}
		if (Greenfoot.isKeyDown("space")) {
			if (speed > stopped && counter % 3 == brakes) {
				speed--;
			} else if (speed < stopped && counter % 3 == brakes) {
				speed++;
			}
		}
		if (speed > stopped && counter == counterTime)
			speed--;
		else if (speed < stopped && counter == counterTime)
			speed++;

		this.move(speed);
	}
}
