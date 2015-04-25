package model;

import greenfoot.Actor;
import greenfoot.GreenfootImage;


public class Road extends Actor{
	public static int roadWidth;
	public Road(String image) {
		GreenfootImage roadImg = new GreenfootImage(image);
		this.setImage(roadImg);
	}
	
	public static int getRoadWidth(){
		return roadWidth;
	}
}
