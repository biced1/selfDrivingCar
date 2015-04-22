package control;

import greenfoot.World;
import model.Curb;
import model.DisplayPanel;
import model.Divider;
import model.Road;

public class TestWorld extends World {

	private static int roadSize = 256;
	private static int width = 5;
	private static int height = 3;
	private static int worldWidth = width * roadSize;
	private static int worldHeight = height * roadSize;
	private static int cellSize = 1;
	private static int curbWidth = 60;
	private static int dividerWidth = 2;
	private static final int displayPanelWidth = 100;

	public TestWorld() {
		super(worldWidth + displayPanelWidth, worldHeight, cellSize);

		// int[] map = { 6, 1, 4, 2, 0, 2, 2, 0, 2, 7, 1, 5, 0, 0, 0 };
		int[] map = { 6, 9, 4, 2, 2, 2, 'b', 3, 'a', 2, 2, 2, 7, 8, 5 };
		addRoads(map, width, height);
		Car car = new Car();
		addCar(car, 150, 100);
		addDisplayPanel(car);
		this.addObject(new ToggleKeyListener(), 1, 1);
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
	
	private void addDisplayPanel(Car car){
		this.addObject(new DisplayPanel(car, displayPanelWidth, worldHeight), worldWidth + displayPanelWidth/2, worldHeight/2);
	}

	private void addTS(int width, int height) {
		this.addObject(new Road("images/roads/roadTS.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);
		addTopCurb(width, height);
		addBottomLeftCurb(width, height);
		addBottomRightCurb(width, height);
		addBottomDivider(width, height);
		addLeftDivider(width, height);
		addRightDivider(width, height);
	}

	private void addTN(int width, int height) {
		this.addObject(new Road("images/roads/roadTN.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);
		addBottomCurb(width, height);
		addTopRightCurb(width, height);
		addTopLeftCurb(width, height);
		addTopDivider(width, height);
		addLeftDivider(width, height);
		addRightDivider(width, height);
	}

	private void addTE(int width, int height) {
		this.addObject(new Road("images/roads/roadTE.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);
		addLeftCurb(width, height);
		addTopRightCurb(width, height);
		addBottomRightCurb(width, height);
		addRightDivider(width, height);
		addTopDivider(width, height);
		addBottomDivider(width, height);
	}

	private void addTW(int width, int height) {
		this.addObject(new Road("images/roads/roadTW.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);
		addRightCurb(width, height);
		addTopLeftCurb(width, height);
		addBottomLeftCurb(width, height);
		addTopDivider(width, height);
		addBottomDivider(width, height);
		addLeftDivider(width, height);
	}

	private void addSW(int width, int height) {
		this.addObject(new Road("images/roads/roadSW.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);
		addTopCurb(width, height);
		addRightCurb(width, height);
		addBottomLeftCurb(width, height);
		addBottomDivider(width, height);
		addLeftDivider(width, height);
	}

	private void addSE(int width, int height) {
		this.addObject(new Road("images/roads/roadSE.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);
		addTopCurb(width, height);
		addLeftCurb(width, height);
		addBottomRightCurb(width, height);
		addBottomDivider(width, height);
		addRightDivider(width, height);
	}

	private void addNW(int width, int height) {
		this.addObject(new Road("images/roads/roadNW.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);
		addRightCurb(width, height);
		addBottomCurb(width, height);
		addTopLeftCurb(width, height);
		addTopDivider(width, height);
		addLeftDivider(width, height);
	}

	private void addNE(int width, int height) {
		this.addObject(new Road("images/roads/roadNE.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);
		addLeftCurb(width, height);
		addBottomCurb(width, height);
		addTopRightCurb(width, height);
		addTopDivider(width, height);
		addRightDivider(width, height);
	}

	private void addNEWS(int width, int height) {
		this.addObject(new Road("images/roads/roadNEWS.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);
		addTopRightCurb(width, height);
		addTopLeftCurb(width, height);
		addBottomRightCurb(width, height);
		addBottomLeftCurb(width, height);
		addTopDivider(width, height);
		addBottomDivider(width, height);
		addLeftDivider(width, height);
		addRightDivider(width, height);
	}

	private void addEW(int width, int height) {
		this.addObject(new Road("images/roads/roadEW.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);
		addTopCurb(width, height);
		addBottomCurb(width, height);
		addLeftDivider(width, height);
		addRightDivider(width, height);
	}

	private void addNS(int width, int height) {
		this.addObject(new Road("images/roads/roadNS.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);
		addLeftCurb(width, height);
		addRightCurb(width, height);
		addTopDivider(width, height);
		addBottomDivider(width, height);
	}

	private void addPlaza(int width, int height) {
		this.addObject(new Road("images/roads/roadPLAZA.jpg"), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);
		this.addObject(new Curb(roadSize, roadSize), width * roadSize
				+ roadSize / 2, height * roadSize + roadSize / 2);

	}

	private void addTopDivider(int width, int height) {
		this.addObject(new Divider(dividerWidth, roadSize / 2), width
				* roadSize + roadSize / 2, height * roadSize + roadSize / 4);
	}

	private void addBottomDivider(int width, int height) {
		this.addObject(new Divider(dividerWidth, roadSize / 2), width
				* roadSize + roadSize / 2, (height + 1) * roadSize - roadSize / 4);
	}

	private void addLeftDivider(int width, int height) {
		this.addObject(new Divider(roadSize / 2, dividerWidth), width
				* roadSize + roadSize / 4, height * roadSize + roadSize / 2);
	}

	private void addRightDivider(int width, int height) {
		this.addObject(new Divider(roadSize / 2, dividerWidth), (width + 1)
				* roadSize - roadSize / 4, height * roadSize + roadSize / 2);
	}

	private void addTopCurb(int width, int height) {
		this.addObject(new Curb(roadSize, curbWidth), width * roadSize
				+ roadSize / 2, height * roadSize + curbWidth / 2);
	}

	private void addBottomCurb(int width, int height) {
		this.addObject(new Curb(roadSize, curbWidth), width * roadSize
				+ roadSize / 2, (height + 1) * roadSize - curbWidth / 2);
	}

	private void addLeftCurb(int width, int height) {
		this.addObject(new Curb(curbWidth, roadSize), width * roadSize
				+ curbWidth / 2, height * roadSize + roadSize / 2);
	}

	private void addRightCurb(int width, int height) {
		this.addObject(new Curb(curbWidth, roadSize), (width + 1) * roadSize
				- curbWidth / 2, height * roadSize + roadSize / 2);
	}

	private void addTopRightCurb(int width, int height) {
		this.addObject(new Curb(curbWidth, curbWidth), (width + 1) * roadSize
				- curbWidth / 2, height * roadSize + curbWidth / 2);
	}

	private void addTopLeftCurb(int width, int height) {
		this.addObject(new Curb(curbWidth, curbWidth), width * roadSize
				+ curbWidth / 2, height * roadSize + curbWidth / 2);
	}

	private void addBottomRightCurb(int width, int height) {
		this.addObject(new Curb(curbWidth, curbWidth), (width + 1) * roadSize
				- curbWidth / 2, (height + 1) * roadSize - curbWidth / 2);
	}

	private void addBottomLeftCurb(int width, int height) {
		this.addObject(new Curb(curbWidth, curbWidth), width * roadSize
				+ curbWidth / 2, (height + 1) * roadSize - curbWidth / 2);
	}

	public void addCar(Car car, int startXPos, int startYPos) {
		this.addObject(car, startXPos, startYPos);
	}
}
