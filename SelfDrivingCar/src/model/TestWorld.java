package model;

import greenfoot.World;
import control.Car;
import control.Curb;
import control.Road;

public class TestWorld extends World {

	public static int worldWidth = 1500;
	public static int worldHeight = 800;
	public static int cellSize = 1;
	public static int numberOfRoads = 4;
	public static int roadOffset = 50;

	public TestWorld() {
		super(worldWidth, worldHeight, cellSize);
		addRoads();
		addCar(100, 100);

	}

	public void addRoads() {
		int roadSegmentWidth = 200;
		for (int x = 0; x < numberOfRoads; x++) {
			for (int y = 0; y < worldWidth / roadSegmentWidth + 1; y++) {
				int xPos = roadSegmentWidth / 2 + roadSegmentWidth * y;
				int yPos = (worldHeight / numberOfRoads * x) + roadOffset
						+ Curb.getCurbWidth() / 2;
				this.addObject(new Curb(roadSegmentWidth), xPos, yPos);

				yPos = (worldHeight / numberOfRoads * x) + roadOffset
						+ Curb.getCurbWidth() + Road.getRoadWidth() / 2;
				this.addObject(new Road(roadSegmentWidth), xPos, yPos);
				
				yPos = (worldHeight / numberOfRoads * x) + roadOffset
						+ Curb.getCurbWidth() + Road.getRoadWidth();
				this.addObject(new Curb(roadSegmentWidth), xPos, yPos);
			}
		}
	}

	public void addCar(int startXPos, int startYPos) {
		Car car = new Car();
		this.addObject(car, startXPos, startYPos);
	}
}
