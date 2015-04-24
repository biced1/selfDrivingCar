package control;

import greenfoot.World;

import java.util.ArrayList;
import java.util.List;

import model.Map;

public class TestWorld2 extends World {

	public static int worldWidth = 1900;
	public static int worldHeight = 900;
	private static int cellSize = 1;

	public TestWorld2() {
		super(worldWidth, worldHeight, cellSize, false);
		List<Car> cars = new ArrayList<Car>();
		cars.add(new Car());
		Map map = new Map(cars);
		this.addObject(map, map.getImage().getWidth() / 2, map.getImage().getHeight() /2);
		cars.get(0).setMap(map);
		this.addObject(cars.get(0).getFront(), 135, 100);
		this.addObject(cars.get(0).getRear(), 65, 100);
		this.addObject(cars.get(0), 100, 100);
	}

}
