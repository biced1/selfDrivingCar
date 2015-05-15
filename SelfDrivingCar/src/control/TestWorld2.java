package control;

import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import greenfoot.World;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import model.Tile;
import view.SetupInput;

public class TestWorld2 extends World {
	private double viewFrameX = 0;
	private double viewFrameY = 0;

	public static int worldWidth = 1800;
	public static int worldHeight = 900;
	private static int cellSize = 1;

	private boolean isFocused = true;

	private static int panSpeed = 10;

	// private Map map;

	private int currentCenter;
	private List<Car> cars = new ArrayList<Car>();

	private List<Tile> tiles = new ArrayList<Tile>();
	private Tile middleTile;

	private static int carInitialRotation = 0;

	private static int carRadius = 22;

	private static int quarterCircle = 90;
	private static int halfCircle = 2 * quarterCircle;
	private static int threeQuarterCircle = 3 * quarterCircle;
	private SetupInput setup = new SetupInput();
	private MapIO mapDownloader = new MapIO();
	private TileManager manager;

	public TestWorld2() {
		super(worldWidth, worldHeight, cellSize, false);
		setup.displayInputs();
		List<Tile> allTiles = mapDownloader.getTiles(setup.getStartLatitude(), setup.getStartLongitude(), setup.getEndLatitude(), setup.getEndLongitude());
		manager = new TileManager(allTiles);
		this.setActOrder(Tile.class, Car.class, FrontTire.class, BackTire.class);
	}

	private void addTile(Tile t) {

		double tileXPos = t.getXPos() * t.getImage().getWidth() + t.getImage().getWidth() / 2;
		double tileYPos = t.getYPos() * t.getImage().getHeight() + t.getImage().getHeight() / 2;
		this.addObject(t, (int) tileXPos, (int) tileYPos);
	}

	private void addCar(int x, int y, int rotation) {
		List<Ray> carRays = new ArrayList<Ray>();
		int raysStartingDegree = 0;
		int raysEndingDegree = 359;
		int rayFrequency = 1;

		for (int i = raysStartingDegree; i <= raysEndingDegree; i += rayFrequency) {
			Ray ray = new Ray(x, y, i, i);
			this.addObject(ray, x, y);
			carRays.add(ray);
		}
		Car car = new Car(carRays);
		car.setRotation(rotation);
		this.addObject(car, x, y);
		this.addObject(car.getFront(), (int) (x + carRadius * getXComponent(rotation)), (int) (y + carRadius * getYComponent(rotation)));
		this.addObject(car.getRear(), (int) (x - carRadius * getXComponent(rotation)), (int) (y - carRadius * getYComponent(rotation)));
		cars.add(car);
	}

	public Color getColor(double xPos, double yPos) {
		int colorXPos = (int) (xPos - viewFrameX);
		int colorYPos = (int) (yPos - viewFrameY);
		if(colorXPos < 0){
			colorXPos = 0;
		}
		if(colorYPos < 0){
			colorYPos = 0;
		}
		return manager.getColorAt(colorXPos, colorYPos);
	}

	private void setNextAsCurrent() {
		int firstCar = 0;
		int lastCar = cars.size() - 1;
		currentCenter++;
		if (currentCenter > lastCar) {
			currentCenter = firstCar;
		}
	}

	private void setPrevAsCurrent() {
		int firstCar = 0;
		int lastCar = cars.size() - 1;
		currentCenter -= 1;
		if (currentCenter < firstCar) {
			currentCenter = lastCar;
		}
	}

	private void addCarAtCurrentMousePosition(int rotation) {
		MouseInfo mouse = Greenfoot.getMouseInfo();
		if (mouse != null) {
			Color testColor = getColor(mouse.getX(), mouse.getY());
			if (ColorValidation.isRoadColor(testColor)) {
				addCar(mouse.getX(), mouse.getY(), rotation);
			}
		}
	}

	@Override
	public void act() {
		respondToKeyEvent();
		setActors();
		setCenter();
	}

	private void setCenter() {
		int xMiddle = worldWidth / 2;
		int yMiddle = worldHeight / 2;

		int tileCenterX = (int) (xMiddle - viewFrameX);
		int tileCenterY = (int) (yMiddle - viewFrameY);

		Tile testTile = manager.getTileAt(tileCenterX, tileCenterY);

		if (testTile != null && !testTile.equals(middleTile)) {
			removeCurrentTiles();
			middleTile = testTile;
			addNewTiles(tileCenterX, tileCenterY);
		}
	}

	private void removeCurrentTiles() {
		List<Tile> toRemove = manager.getCurrentTiles();
		for (Tile t : toRemove) {
			this.removeObject(t);
		}
		manager.clearCurrentTiles();
	}

	private void addNewTiles(int tileCenterX, int tileCenterY) {
		tiles = manager.getTileSection(tileCenterX, tileCenterY);
		for (Tile t : tiles) {
			addTile(t);
		}
	}

	private void setActors() {
		int xMiddle = worldWidth / 2;
		int yMiddle = worldHeight / 2;

		double xChange = 0;
		double yChange = 0;

		if (isFocused && !cars.isEmpty()) {
			xChange = xMiddle - cars.get(currentCenter).getExactX();
			yChange = yMiddle - cars.get(currentCenter).getExactY();
		}
		viewFrameX += xChange;
		viewFrameY += yChange;
		setTiles(viewFrameX, viewFrameY);
		setCars(xChange, yChange);

	}

	private void setTiles(double viewFrameX, double viewFrameY) {
		for (Tile t : tiles) {
			double tileXPos = t.getXPos() * t.getImage().getWidth() + viewFrameX + t.getImage().getWidth() / 2;
			double tileYPos = t.getYPos() * t.getImage().getHeight() + viewFrameY + t.getImage().getHeight() / 2;
			t.setLocation(tileXPos, tileYPos);
		}
	}

	private void setCars(double xChange, double yChange) {
		for (Car c : cars) {
			c.getFront().setLocation(c.getFront().getExactX() + xChange, c.getFront().getExactY() + yChange);
			c.getRear().setLocation(c.getRear().getExactX() + xChange, c.getRear().getExactY() + yChange);
			c.setPosition();
		}
	}

	private void respondToKeyEvent() {
		String key = Greenfoot.getKey();
		if ("z".equals(key) || "Z".equals(key)) {
			isFocused = true;
			setPrevAsCurrent();
		}
		if ("x".equals(key) || "X".equals(key)) {
			isFocused = true;
			setNextAsCurrent();
		}
		if ("l".equals(key) || "L".equals(key)) {
			addCarAtCurrentMousePosition(carInitialRotation);
		}
		if ("k".equals(key) || "K".equals(key)) {
			addCarAtCurrentMousePosition(quarterCircle);
		}
		if ("j".equals(key) || "J".equals(key)) {
			addCarAtCurrentMousePosition(halfCircle);
		}
		if ("i".equals(key) || "I".equals(key)) {
			addCarAtCurrentMousePosition(threeQuarterCircle);
		}
		if (Greenfoot.isKeyDown("up")) {
			isFocused = false;
			viewFrameY += panSpeed;
			pan(0, panSpeed);
		}
		if (Greenfoot.isKeyDown("down")) {
			isFocused = false;
			viewFrameY -= panSpeed;
			pan(0, -panSpeed);
		}
		if (Greenfoot.isKeyDown("left")) {
			isFocused = false;
			viewFrameX += panSpeed;
			pan(panSpeed, 0);
		}
		if (Greenfoot.isKeyDown("right")) {
			isFocused = false;
			viewFrameX -= panSpeed;
			pan(-panSpeed, 0);
		}
	}

	private void pan(int x, int y) {
		for (Car car : cars) {
			car.getFront().setLocation(car.getFront().getExactX() + x, car.getFront().getExactY() + y);
			car.getRear().setLocation(car.getRear().getExactX() + x, car.getRear().getExactY() + y);
			car.setPosition();
		}
	}

	private double getXComponent(int degrees) {
		return Math.cos(degrees * Math.PI / halfCircle);
	}

	private double getYComponent(int degrees) {
		return Math.sin(degrees * Math.PI / halfCircle);
	}
}
