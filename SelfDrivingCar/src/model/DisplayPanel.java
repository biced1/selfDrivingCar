//package model;
//
//import greenfoot.Actor;
//import greenfoot.GreenfootImage;
//
//import java.awt.Color;
//import java.awt.Font;
//
//import control.Car;
//
//public class DisplayPanel extends Actor {
//	private Car car;
//	private int width;
//	private int height;
//	private GreenfootImage displayPanelImage;
//	private final String DEGREE = "\u00b0";
//	private final int TEXTHEIGHT = 10;
//
//	@Override
//	public void act() {
//		displayPanelImage.clear();
//		drawSpeed();
//		drawRotation();
//	}
//
//	public DisplayPanel(Car car, int width, int height) {
//		this.car = car;
//		this.width = width;
//		this.height = height;
//		displayPanelImage = new GreenfootImage(width, height);
//		displayPanelImage.setColor(new Color(0, 0, 0));
//		displayPanelImage.setFont(new Font("TimesRoman", Font.PLAIN, 12));
//		drawSpeed();
//		drawRotation();
//		this.setImage(displayPanelImage);
//	}
//
//	private void drawSpeed() {
//		displayPanelImage.drawString("Speed:" + car.getSpeed(), 0, TEXTHEIGHT);
//	}
//
//	private void drawRotation() {
//		displayPanelImage.drawString("Rotation: " + car.getRotation() + DEGREE,
//				0, TEXTHEIGHT * 2);
//	}
//
//	public int getHeight() {
//		return this.height;
//	}
//
//	public int getWidth() {
//		return this.width;
//	}
//
//}
