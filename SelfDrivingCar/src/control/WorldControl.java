package control;

import model.TestWorld;
import greenfoot.Greenfoot;
import greenfoot.World;

public class WorldControl {
	public void setup(){
		World world = new TestWorld();
		Greenfoot.setSpeed(10);
		Greenfoot.setWorld(world);
	}
}