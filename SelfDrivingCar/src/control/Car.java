package control;

import greenfootAdditions.SmoothMover;

import java.util.List;

import model.Map;

public class Car extends SmoothMover {
	private FrontTire front = new FrontTire();
	private SmoothMover rear = new BackTire();
	private List<Ray> rays;

	public Car(List<Ray> rays) {
		super();
		this.rays = rays;
		setRed();
		setBlue();
	}

	private void setPosition() {
		this.setLocation((front.getExactX() + rear.getExactX()) / 2,
				(front.getExactY() + rear.getExactY()) / 2);
		this.setRotation(rear.getRotation());
	}

	@Override
	public void act() {
		for (Ray r : rays) {
			r.reset(this.getExactX(), this.getExactY(), this.getRotation(),
					this.getFront().getMovement().getLength());
			while (!r.isDistanceReached() && !r.hitCurb()) {
				r.step();
			}
		}
		setPosition();
		if (this.isTouching(Map.class)) {
			this.setBlue();
		} else {
			this.setRed();
		}
		if (this.getFront().getSpeed() < 2) {
			this.getFront().accelerate();
		}

		int bestRotation = getBestDirection();
		if (clockwiseDegreesAway(this.getRotation(), bestRotation) < counterClockwiseDegreesAway(
				this.getRotation(), bestRotation)) {
			this.front.turnRight();
		} else if (clockwiseDegreesAway(this.getRotation(), bestRotation) > counterClockwiseDegreesAway(
				this.getRotation(), bestRotation)) {
			this.front.turnLeft();
		} else {
			if (clockwiseDegreesAway(this.getRotation(),
					this.front.getRotation()) < 50
					&& clockwiseDegreesAway(this.getRotation(),
							this.front.getRotation()) > 0) {
				this.front.turnLeft();
			} else if (counterClockwiseDegreesAway(this.getRotation(),
					this.front.getRotation()) < 50
					&& counterClockwiseDegreesAway(this.getRotation(),
							this.front.getRotation()) > 0) {
				this.front.turnRight();
			}
		}
	}

	private int getBestDirection() {
		int bestDirection = 0;
		double bestValue = 0;
		for (Ray ray : rays) {
			int clockwiseAway = clockwiseDegreesAway(this.getRotation(),
					ray.getRotation());
			int counterAway = counterClockwiseDegreesAway(this.getRotation(),
					ray.getRotation());
			int degreesAway = clockwiseAway < counterAway ? clockwiseAway
					: counterAway;
			double value = ray.getDistance() - (degreesAway);
			
			if (value > bestValue) {
				bestValue = value;
				bestDirection = ray.getRotation();
			}
		}
		return bestDirection;
	}

	private int clockwiseDegreesAway(int rotationFacing, int rotationToTurn) {
		int degreesAway = 0;
		while (rotationFacing != rotationToTurn) {
			rotationFacing++;
			if (rotationFacing > 359) {
				rotationFacing -= 360;
			}
			degreesAway++;
		}
		return degreesAway;
	}

	private int counterClockwiseDegreesAway(int rotationFacing,
			int rotationToTurn) {
		int degreesAway = 0;
		while (rotationFacing != rotationToTurn) {
			rotationFacing--;
			if (rotationFacing < 0) {
				rotationFacing += 360;
			}
			degreesAway++;
		}
		return degreesAway;

	}

	private void setBlue() {
		this.setImage("images/regular/blueCar.png");
	}

	private void setRed() {
		this.setImage("images/regular/redCar.png");
	}

	public FrontTire getFront() {
		return front;
	}

	public SmoothMover getRear() {
		return rear;
	}
}
