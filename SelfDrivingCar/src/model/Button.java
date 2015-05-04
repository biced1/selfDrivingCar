package model;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

import java.awt.Color;

public class Button extends Actor{
	public Button(){
		GreenfootImage image = new GreenfootImage(25, 25);
		image.setColor(new Color(88, 88, 88));
		image.fillOval(0, 0, 25, 25);
		this.setImage(image);
	}
}
