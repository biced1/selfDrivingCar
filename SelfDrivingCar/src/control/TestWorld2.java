package control;

import greenfoot.World;

import java.util.ArrayList;
import java.util.List;

import model.Map;

public class TestWorld2 extends World {

	public static int worldWidth = 1900;
	public static int worldHeight = 900;
	private static int cellSize = 1;
	private List<Car> cars = new ArrayList<Car>();

	public TestWorld2() {
		super(worldWidth, worldHeight, cellSize, false);
		addMap();
		addCar(100, 100);
	}
	private void addMap(){
		Map map = new Map(cars);
		this.addObject(map, map.getImage().getWidth() / 2, map.getImage().getHeight() /2);
	}
	
	private void addCar(int x, int y){
		Car car = new Car();
		this.addObject(car.getFront(), x + 35, y);
		this.addObject(car.getRear(), x - 35, y);
		this.addObject(car, x, y);
		cars.add(car);
	}

}
