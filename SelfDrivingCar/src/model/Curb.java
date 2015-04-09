package model;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

import java.awt.Color;

public class Curb extends Actor {
	private int curbWidth;
	private int curbLength;
	private GreenfootImage curbImg;
	public static boolean visible = false;

	@Override
	public void act() {
		if (visible) {
			view();
		} else {
			hide();
		}
	}

	public Curb(int curbLength, int curbWidth) {
		this.curbLength = curbLength;
		this.curbWidth = curbWidth;
		curbImg = new GreenfootImage(curbLength, curbWidth);
		curbImg.setColor(new Color(84, 84, 84));
		curbImg.fill();
		hide();
		this.setImage(curbImg);
	}

	private void view() {
		curbImg.setTransparency(255);
	}

	private void hide() {
		curbImg.setTransparency(0);
	}

	public int getCurbWidth() {
		return curbWidth;
	}

	public int getCurbLength() {
		return curbLength;
	}
}
