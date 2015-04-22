package control;

import java.awt.Color;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfootAdditions.SmoothMover;

public abstract class Tire extends SmoothMover {
	protected final double maxSpeed = 5;

	public Tire() {
		GreenfootImage tire = new GreenfootImage(10, 10);
		tire.setColor(new Color(84, 84, 84));
		tire.fill();
		this.setImage(tire);
	}

	@Override
	public void act() {
		if (Greenfoot.isKeyDown("a") || Greenfoot.isKeyDown("A")) {
			turnLeft();
		}
		if (Greenfoot.isKeyDown("d") || Greenfoot.isKeyDown("D")) {
			turnRight();
		}
		if (Greenfoot.isKeyDown("s") || Greenfoot.isKeyDown("S")) {
			reverse();
		}
		if (Greenfoot.isKeyDown("w") || Greenfoot.isKeyDown("W")) {
			accelerate();
		}
		if (Greenfoot.isKeyDown("space")) {
			brake();
		}
		move();
	}

	protected abstract void brake();

	protected abstract void accelerate();

	protected abstract void reverse();

	protected abstract void turnRight();

	protected abstract void turnLeft();

}
