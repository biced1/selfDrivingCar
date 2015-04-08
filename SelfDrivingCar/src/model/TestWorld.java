package model;

import greenfoot.World;
import control.Car;
import control.Curb;

public class TestWorld extends World{
	
	public static int worldWidth = 1500;
	public static int worldHeight = 800;
	public static int cellSize = 1;
	
	public TestWorld() {
		super(worldWidth, worldHeight, cellSize);
		this.addObject(new Car(), 100, 100);
		this.addObject(new Curb(), 10, 10);
	}

}
