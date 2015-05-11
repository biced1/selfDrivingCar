package control;

import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import greenfoot.World;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import model.Map;

public class TestWorld2 extends World {
	private double viewFrameX = 0;
	private double viewFrameY = 0;

	public static int worldWidth = 1900;
	public static int worldHeight = 900;
	private static int cellSize = 1;

	private Map map;

	private int currentCenter;
	private List<Car> cars = new ArrayList<Car>();

	private static int carInitialX = 100;
	private static int carInitialY = 325;
	private static int carInitialRotation = 0;

	private static int carRadius = 22;

	private static int quarterCircle = 90;
	private static int halfCircle = 2 * quarterCircle;
	private static int threeQuarterCircle = 3 * quarterCircle;

	public TestWorld2() {
		super(worldWidth, worldHeight, cellSize, false);
		addMap();
		addCar(carInitialX, carInitialY, carInitialRotation);
	}

	private void addMap() {
		map = new Map(cars);

		int mapStartX = map.getImage().getWidth() / 2;
		int mapStartY = map.getImage().getHeight() / 2;
		this.addObject(map, mapStartX, mapStartY);
	}

	private void addCar(int x, int y, int rotation) {
		List<Ray> carRays = new ArrayList<Ray>();
		int raysStartingDegree = 0;
		int raysEndingDegree = 359;
		int rayFrequency = 10;

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
		return map.getImage().getColorAt((int) (xPos - viewFrameX), (int) (yPos - viewFrameY));
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
		int invalidRedValue = 224;
		int redErrorThreshold = 0;
		int invalidBlueValue = 235;
		int blueErrorThreshold = 5;
		int invalidGreenValue = 228;
		int greenErrorThreshold = 5;

		MouseInfo mouse = Greenfoot.getMouseInfo();
		if (mouse != null) {
			Color testColor = getColor(mouse.getX(), mouse.getY());
			if (!(isWithinThreshold(testColor.getRed(), invalidRedValue, redErrorThreshold)
					&& isWithinThreshold(testColor.getBlue(), invalidBlueValue, blueErrorThreshold) && isWithinThreshold(testColor.getGreen(),
						invalidGreenValue, greenErrorThreshold))) {
				addCar(mouse.getX(), mouse.getY(), rotation);
			}
		}
	}

	private boolean isWithinThreshold(int testValue, int prefferedValue, int threshold) {
		return prefferedValue - threshold <= testValue && testValue <= prefferedValue + threshold;
	}

	@Override
	public void act() {
		setActors();
		respondToKeyEvent();
	}

	private void setActors() {
		int xMiddle = worldWidth / 2;
		int yMiddle = worldHeight / 2;

		double xChange = xMiddle - cars.get(currentCenter).getExactX();
		double yChange = yMiddle - cars.get(currentCenter).getExactY();

		viewFrameX += xChange;
		viewFrameY += yChange;

		double mapX = map.getImage().getWidth() / 2 + viewFrameX;
		double mapY = map.getImage().getHeight() / 2 + viewFrameY;

		map.setLocation(mapX, mapY);
		for (Car c : cars) {
			c.getFront().setLocation(c.getFront().getExactX() + xChange, c.getFront().getExactY() + yChange);
			c.getRear().setLocation(c.getRear().getExactX() + xChange, c.getRear().getExactY() + yChange);
		}
	}

	private void respondToKeyEvent() {
		String key = Greenfoot.getKey();
		if ("z".equals(key) || "Z".equals(key)) {
			setPrevAsCurrent();
		}
		if ("x".equals(key) || "X".equals(key)) {
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
	}

	private double getXComponent(int degrees) {
		return Math.cos(degrees * Math.PI / halfCircle);
	}

	private double getYComponent(int degrees) {
		return Math.sin(degrees * Math.PI / halfCircle);
	}
}
