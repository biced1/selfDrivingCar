package control;

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
	private Car currentCenter;
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
		List<Ray> rays = new ArrayList<Ray>();
		for(int i = 0; i < 360; i += 1){
			System.out.println(i);
			Ray ray = new Ray(x, y, i, i);
			this.addObject(ray, x, y);
			rays.add(ray);
		}
		System.out.println(rays.size());
		Car car = new Car(rays);
		currentCenter = car;
		this.addObject(car, x, y);
		this.addObject(car.getFront(), x + 22, y);
		this.addObject(car.getRear(), x - 22, y);
		cars.add(car);
	}
	
	public Color getColor(double xPos, double yPos){
		return map.getImage().getColorAt((int)(xPos - viewFrameX), (int)(yPos - viewFrameY));
		
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
