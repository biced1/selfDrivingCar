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

	public TestWorld2() {
		super(worldWidth, worldHeight, cellSize, false);
		addMap();
		addCar(100, 325, 0);
	}

	private void addMap() {
		map = new Map(cars);
		this.addObject(map, map.getImage().getWidth() / 2, map.getImage()
				.getHeight() / 2);
	}

	private void addCar(int x, int y, int rotation) {
		List<Ray> rays = new ArrayList<Ray>();
		for (int i = 0; i < 360; i += 10) {
			Ray ray = new Ray(x, y, i, i);
			this.addObject(ray, x, y);
			rays.add(ray);
		}
		Car car = new Car(rays);
		car.setRotation(rotation);
		this.addObject(car, x, y);
		this.addObject(car.getFront(), (int)(x + 22 * Math.cos(rotation * Math.PI/180)), (int)(y + 22 * Math.sin(rotation * Math.PI/180)));
		this.addObject(car.getRear(), (int)(x - 22 * Math.cos(rotation * Math.PI/180)), (int)(y - 22 * Math.sin(rotation * Math.PI/180)));
		cars.add(car);
	}

	public Color getColor(double xPos, double yPos) {
		return map.getImage().getColorAt((int) (xPos - viewFrameX),
				(int) (yPos - viewFrameY));
	}

	private void setNextAsCurrent() {
		currentCenter += 1;
		if (currentCenter >= cars.size()) {
			currentCenter = 0;
		}
	}

	private void setPrevAsCurrent() {
		currentCenter -= 1;
		if (currentCenter < 0) {
			currentCenter = cars.size() - 1;
		}
	}

	private void addCarAtMousePosition(int rotation) {
		MouseInfo mouse = Greenfoot.getMouseInfo();
		if (mouse != null) {
			Color c = getColor(mouse.getX(), mouse.getY());
			if (!(c.getRed() == 224 && 230 < c.getBlue() && c.getBlue() < 240
					&& 223 < c.getGreen() && c.getGreen() < 233)) {
				addCar(mouse.getX(), mouse.getY(), rotation);
			}
		}
	}

	@Override
	public void act() {
		int xMiddle = worldWidth / 2;
		int yMiddle = worldHeight / 2;
		double xChange = xMiddle - cars.get(currentCenter).getExactX();
		double yChange = yMiddle - cars.get(currentCenter).getExactY();
		viewFrameX += xChange;
		viewFrameY += yChange;
		map.setLocation(map.getImage().getWidth() / 2 + viewFrameX, map
				.getImage().getHeight() / 2 + viewFrameY);
		for (Car c : cars) {
			c.getFront().setLocation(c.getFront().getExactX() + xChange,
					c.getFront().getExactY() + yChange);
			c.getRear().setLocation(c.getRear().getExactX() + xChange,
					c.getRear().getExactY() + yChange);
		}
		String key = Greenfoot.getKey();
		if ("z".equals(key) || "Z".equals(key)) {
			setPrevAsCurrent();
		}
		if ("x".equals(key) || "X".equals(key)) {
			setNextAsCurrent();
		}
		if ("l".equals(key) || "L".equals(key)) {
			addCarAtMousePosition(0);
		}
		if ("k".equals(key) || "K".equals(key)) {
			addCarAtMousePosition(90);
		}
		if ("j".equals(key) || "J".equals(key)) {
			addCarAtMousePosition(180);
		}
		if ("i".equals(key) || "I".equals(key)) {
			addCarAtMousePosition(270);
		}

	}
}
