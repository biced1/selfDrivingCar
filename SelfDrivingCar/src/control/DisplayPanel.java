package control;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import model.Coordinate;

public class DisplayPanel extends Actor {
	private int width;
	private int height;
	private GreenfootImage displayPanelImage;
	private final int TEXTHEIGHT = 12;
	private int displayIndex = 2;

	private int speedIndex;
	private int rotationIndex;
	private int locationLatIndex;
	private int locationLongIndex;
	private int firstCoordinateLatIndex;
	private int firstCoordinateLongIndex;
	private int secondCoordinateLatIndex;
	private int secondCoordinateLongIndex;
	
	private Coordinate firstCoordinate = new Coordinate(0, 0);
	private Coordinate secondCoordinate = new Coordinate(0, 0);
	private double speed = 0;
	private int rotation = 0;
	private Coordinate currentLocation = new Coordinate(0, 0);
	
	private List<String> moreLines = new ArrayList<String>();

	@Override
	public void act() {
		displayPanelImage.clear();
		updatePanel();
	}

	public DisplayPanel(int width, int height) {
		this.width = width;
		this.height = height;
		setupImage();
		drawDefaults();
	}

	private void setupImage() {
		int redValue = 255;
		int greenValue = 0;
		int blueValue = 0;
		int fontSize = 12;
		displayPanelImage = new GreenfootImage(width, height);
		displayPanelImage.setColor(new Color(redValue, greenValue, blueValue));
		displayPanelImage.setFont(new Font("TimesRoman", Font.PLAIN, fontSize));
		this.setImage(displayPanelImage);
	}

	private void drawDefaults() {
		drawSpeed();
		drawRotation();
		drawLocation();
		drawLine();
		drawFirstCoordinate();
		drawSecondCoordinate();
		drawLine();
	}
	
	private void updatePanel(){
		updateFirstCoordinate(firstCoordinate);
		updateSecondCoordinate(secondCoordinate);
		updateSpeed(speed);
		updateRotation(rotation);
		updateLocation(currentLocation);
		drawMoreLines();
	}
	
	private void drawMoreLines(){
		for(int pos = 0; pos < moreLines.size(); pos++){
			displayPanelImage.drawString(moreLines.get(pos), 0, TEXTHEIGHT * displayIndex + pos);			
		}
	}
	
	public void addLine(String line){
		moreLines.add(line);
	}

	private void updateFirstCoordinate(Coordinate location) {
		displayPanelImage.drawString("First Coord Lat: " + location.getLatitude(), 0, TEXTHEIGHT * firstCoordinateLatIndex);
		displayPanelImage.drawString("First Coord Long: " + location.getLongitude(), 0, TEXTHEIGHT * firstCoordinateLongIndex);
	}

	private void updateSecondCoordinate(Coordinate location) {
		displayPanelImage.drawString("Second Coord Lat: " + location.getLatitude(), 0, TEXTHEIGHT * secondCoordinateLatIndex);
		displayPanelImage.drawString("Second Coord Long: " + location.getLongitude(), 0, TEXTHEIGHT * secondCoordinateLongIndex);
	}

	private void updateSpeed(double speed) {
		displayPanelImage.drawString("Speed: " + speed, 0, TEXTHEIGHT * speedIndex);
	}

	private void updateRotation(int rotation) {
		displayPanelImage.drawString("Rotation: " + rotation, 0, TEXTHEIGHT * rotationIndex);
	}

	private void updateLocation(Coordinate location) {
		displayPanelImage.drawString("Location Lat: " + location.getLatitude(), 0, TEXTHEIGHT * locationLatIndex);
		displayPanelImage.drawString("Location Long: " + location.getLongitude(), 0, TEXTHEIGHT * locationLongIndex);
	}

	private void drawFirstCoordinate() {
		firstCoordinateLatIndex = displayIndex;
		displayIndex++;
		firstCoordinateLongIndex = displayIndex;
		displayIndex++;
		displayPanelImage.drawString("First Coord Lat: ", 0, TEXTHEIGHT * firstCoordinateLatIndex);
		displayPanelImage.drawString("First Coord Long: ", 0, TEXTHEIGHT * firstCoordinateLongIndex);
	}

	private void drawSecondCoordinate() {
		secondCoordinateLatIndex = displayIndex;
		displayIndex++;
		secondCoordinateLongIndex = displayIndex;
		displayIndex++;
		displayPanelImage.drawString("Second Coord Lat: ", 0, TEXTHEIGHT * secondCoordinateLatIndex);
		displayPanelImage.drawString("Second Coord Long: ", 0, TEXTHEIGHT * secondCoordinateLongIndex);
	}

	private void drawSpeed() {
		speedIndex = displayIndex;
		displayIndex++;
		displayPanelImage.drawString("Speed: ", 0, TEXTHEIGHT * speedIndex);
	}

	private void drawRotation() {
		rotationIndex = displayIndex;
		displayIndex++;
		displayPanelImage.drawString("Rotation: ", 0, TEXTHEIGHT * rotationIndex);
	}

	private void drawLocation() {
		locationLatIndex = displayIndex;
		displayIndex++;
		locationLongIndex = displayIndex;
		displayIndex++;
		displayPanelImage.drawString("Location Lat: ", 0, TEXTHEIGHT * locationLatIndex);
		displayPanelImage.drawString("Location Long: ", 0, TEXTHEIGHT * locationLongIndex);
	}

	private void drawLine() {
		displayIndex++;
	}

	public Coordinate getFirstCoordinate() {
		return firstCoordinate;
	}

	public void setFirstCoordinate(Coordinate firstCoordinate) {
		this.firstCoordinate = firstCoordinate;
	}

	public Coordinate getSecondCoordinate() {
		return secondCoordinate;
	}

	public void setSecondCoordinate(Coordinate secondCoordinate) {
		this.secondCoordinate = secondCoordinate;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public Coordinate getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Coordinate currentLocation) {
		this.currentLocation = currentLocation;
	}

	public int getHeight() {
		return this.height;
	}

	public int getWidth() {
		return this.width;
	}

}
