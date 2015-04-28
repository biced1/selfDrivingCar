package control;

import greenfootAdditions.SmoothMover;

import java.util.List;

import model.Map;

public class Car extends SmoothMover {
	private SmoothMover front = new FrontTire();
	private SmoothMover rear = new BackTire();
	private List<Ray> rays;

	public Car(List<Ray> rays) {
		super();
		this.rays = rays;
		setRed();
		setBlue();
	}

	private void setPosition() {
		this.setLocation((front.getExactX() + rear.getExactX()) / 2, (front.getExactY() + rear.getExactY()) / 2);
		this.setRotation(rear.getRotation());
	}

	@Override
	public void act() {
		for(Ray r : rays){
			r.reset(this.getExactX(), this.getExactY(), this.getRotation(), this.getFront().getMovement().getLength());
			while (!r.isDistanceReached() && !r.hitCurb()){
				r.step();
			}
		}
		setPosition();
		if (this.isTouching(Map.class)) {
			this.setBlue();
		} else {
			this.setRed();
		}
	}

	private void setBlue() {
		this.setImage("images/regular/blueCar.png");
	}

	private void setRed() {
		this.setImage("images/regular/redCar.png");
	}

	public SmoothMover getFront() {
		return front;
	}

	public SmoothMover getRear() {
		return rear;
	}
}
