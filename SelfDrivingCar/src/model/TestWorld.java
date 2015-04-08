package model;

import greenfoot.World;
import control.Car;
import control.Curb;
import control.Road;

public class TestWorld extends World {

	public static int worldWidth = 1800;
	public static int worldHeight = 800;
	public static int cellSize = 1;
	public static int numberOfRoads = 2;
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
				int yPosCurb1 = (worldHeight / numberOfRoads * x) + roadOffset
						+ Curb.getCurbWidth() / 2;

				int yPosRoad = (worldHeight / numberOfRoads * x) + roadOffset
						+ Curb.getCurbWidth() + Road.getRoadWidth() / 2;

				int yPosCurb2 = (worldHeight / numberOfRoads * x) + roadOffset
						+ Curb.getCurbWidth() + Road.getRoadWidth();
				
				this.addObject(new Curb(roadSegmentWidth), xPos, yPosCurb1);
				this.addObject(new Road(roadSegmentWidth), xPos, yPosRoad);
				this.addObject(new Curb(roadSegmentWidth), xPos, yPosCurb2);
			}
		}
	}

	public void addCar(int startXPos, int startYPos) {
		Car car = new Car();
		this.addObject(car, startXPos, startYPos);
	}
}
