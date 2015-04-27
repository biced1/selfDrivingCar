package control;

import greenfoot.World;
import greenfootAdditions.Vector;

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
	private Car currentCenter = new Car();
	private List<Car> cars = new ArrayList<Car>();

	public TestWorld2() {
		super(worldWidth, worldHeight, cellSize, false);
		addMap();
		addCar(worldWidth / 2, worldHeight / 2);
	}

	private void addMap() {
		map = new Map(cars);
		this.addObject(map, map.getImage().getWidth() / 2, map.getImage()
				.getHeight() / 2);
	}

	private void addCar(int x, int y) {
		Car car = currentCenter;
		this.addObject(car.getFront(), x + 35, y);
		this.addObject(car.getRear(), x - 35, y);
		this.addObject(car, x, y);
		cars.add(car);
	}

	@Override
	public void act() {
		int xMiddle = worldWidth / 2;
		int yMiddle = worldHeight / 2;
		double xChange = xMiddle - currentCenter.getExactX();
		double yChange = yMiddle - currentCenter.getExactY();
		viewFrameX += xChange;
		viewFrameY += yChange;
		map.setLocation(map.getImage().getWidth() / 2 + viewFrameX, map
				.getImage().getHeight() / 2 + viewFrameY);
		currentCenter.setLocation(xMiddle, yMiddle);
		currentCenter.getFront().setLocation(
				currentCenter.getFront().getExactX() + xChange,
				currentCenter.getFront().getExactY() + yChange);
		currentCenter.getRear().setLocation(
				currentCenter.getRear().getExactX() + xChange,
				currentCenter.getRear().getExactY() + yChange);
	}
}
