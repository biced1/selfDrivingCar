package model;

import greenfoot.World;
import control.Car;
import control.Curb;
import control.Road;

public class TestWorld extends World {

	private static int roadSize = 256;
	private static int width = 5;
	private static int height = 3;
	private static int worldWidth = width * roadSize;
	private static int worldHeight = height * roadSize;
	private static int cellSize = 1;

	public TestWorld() {
		super(worldWidth, worldHeight, cellSize);
		
		//int[] map = { 6, 1, 4, 2, 0, 2, 2, 0, 2, 7, 1, 5};
		int[] map = { 6, 9, 4, 2, 2, 2, 'b', 3, 'a', 2, 2, 2, 7, 8, 5};
		addRoads(map, width, height);
		addCar(100, 100);

	}

	public void addRoads(int[] map, int width, int height) {
		int pos = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				switch (map[pos]) {
				case 0:
					addPlaza(x, y);
					break;
				case 1:
					addNS(x, y);
					break;
				case 2:
					addEW(x, y);
					break;
				case 3:
					addNEWS(x, y);
					break;
				case 4:
					addNE(x, y);
					break;
				case 5:
					addNW(x, y);
					break;
				case 6:
					addSE(x, y);
					break;
				case 7:
					addSW(x, y);
					break;
				case 8:
					addTW(x, y);
					break;
				case 9:
					addTE(x, y);
					break;
				case 'a':
					addTN(x, y);
					break;
				case 'b':
					addTS(x, y);
					break;
				}
				pos++;
			}
		}
	}

	private void addTS(int width, int height) {
		this.addObject(new Road("images/roads/roadTS.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);

	}

	private void addTN(int width, int height) {
		this.addObject(new Road("images/roads/roadTN.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);

	}

	private void addTE(int width, int height) {
		this.addObject(new Road("images/roads/roadTE.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);

	}

	private void addTW(int width, int height) {
		this.addObject(new Road("images/roads/roadTW.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);

	}

	private void addSW(int width, int height) {
		this.addObject(new Road("images/roads/roadSW.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);

	}

	private void addSE(int width, int height) {
		this.addObject(new Road("images/roads/roadSE.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);

	}

	private void addNW(int width, int height) {
		this.addObject(new Road("images/roads/roadNW.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);

	}

	private void addNE(int width, int height) {
		this.addObject(new Road("images/roads/roadNE.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);

	}

	private void addNEWS(int width, int height) {
		this.addObject(new Road("images/roads/roadNEWS.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);

	}

	private void addEW(int width, int height) {
		this.addObject(new Road("images/roads/roadEW.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);

	}

	private void addNS(int width, int height) {
		this.addObject(new Road("images/roads/roadNS.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);

	}

	private void addPlaza(int width, int height) {
		this.addObject(new Road("images/roads/roadPLAZA.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);

	}

	public void addCar(int startXPos, int startYPos) {
		Car car = new Car();
		this.addObject(car, startXPos, startYPos);
	}
}
