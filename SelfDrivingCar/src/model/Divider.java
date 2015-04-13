package model;

import java.awt.Color;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class Divider extends Actor {
	
	private GreenfootImage dividerImage;
	private int dividerWidth;
	private int dividerLength;
	public static boolean visible = false;
	
	@Override
	public void act() {
		if (visible) {
			view();
		} else {
			hide();
		}
	}
	
	public Divider(int dividerLength, int dividerWidth) {
		this.dividerLength = dividerLength;
		this.dividerWidth = dividerWidth;
		dividerImage = new GreenfootImage(dividerLength, dividerWidth);
		dividerImage.setColor(new Color(255, 255, 0));
		dividerImage.fill();
		hide();
		this.setImage(dividerImage);
	}
	
	private void view() {
		dividerImage.setTransparency(255);
	}

	private void hide() {
		dividerImage.setTransparency(0);
	}

	public int getDividerWidth() {
		return dividerWidth;
	}

	public int getDividerLength() {
		return dividerLength;
	}
}
