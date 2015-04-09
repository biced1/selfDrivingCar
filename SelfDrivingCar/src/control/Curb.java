package control;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

import java.awt.Color;

public class Curb extends Actor {
	private final static int curbWidth = 5;

	public Curb(int curbLength) {
		GreenfootImage curbImg = new GreenfootImage(curbLength, getCurbWidth());
		curbImg.setColor(new Color(84, 84, 84));
		curbImg.fill();
		this.setImage(curbImg);
	}

	public static int getCurbWidth() {
		return curbWidth;
	}
}