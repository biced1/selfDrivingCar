package control;

import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import model.Curb;

public class ToggleKeyListener extends Actor {

	public ToggleKeyListener() {
		this.setImage(new GreenfootImage(1, 1));
	}

	@Override
	public void act() {
		String key = Greenfoot.getKey();
		if (key != null && key.equals("c")) {
			Curb.visible = !Curb.visible;
		}
	}
}
