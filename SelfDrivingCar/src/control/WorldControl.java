package control;

import greenfoot.Greenfoot;
import greenfoot.World;

public class WorldControl {
	public void setup() {
		World world = new TestWorld2();
		Greenfoot.setSpeed(10);
		Greenfoot.setWorld(world);
	}
}
