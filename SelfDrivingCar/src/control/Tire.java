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
		adjustSpeed();
		move();
	}

	public abstract void brake();

	public abstract void accelerate();

	public abstract void reverse();

	public abstract void turnRight();

	public abstract void turnLeft();
	
	protected abstract void adjustSpeed();

}
