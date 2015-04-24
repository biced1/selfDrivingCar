package control;

import java.util.List;

import greenfootAdditions.Vector;

public class FrontTire extends Tire {

	private final int turnRadius = 45;
	private int backTireRotation = 0;
	private int rightTurnMax = turnRadius;
	private int leftTurnMax = -turnRadius;
	private double accelerationDelta = .05;
	private double startForce = .5;
	private static int circle = 360;

	@Override
	public void act() {
		this.updateBackTireDirection();
		this.updateTurnRadius();
		this.adjustAngle();
		// System.out.println(rightTurnMax + " " + leftTurnMax + " "
		// + backTireRotation + " " + this.getRotation());
		super.act();
	}

	@Override
	protected void brake() {
		this.accelerate(.96);
	}

	@Override
	protected void accelerate() {
		int actualRotation = getActualRotation();

		if (this.getSpeed() == 0) {
			this.addForce(new Vector(this.getRotation(), startForce));
		} else if ((actualRotation == this.getRotation() || actualRotation + 1 == this.getRotation() || actualRotation - 1 == this.getRotation())
				&& this.getSpeed() < maxSpeed) {
			this.accelerate(1 + accelerationDelta);
		} else {
			this.accelerate(1 - accelerationDelta);
		}
	}

	@Override
	protected void reverse() {
		int reverseRotation = getReverse(this.getRotation());

		int actualRotation = getActualRotation();

		if (this.getSpeed() == 0) {
			this.addForce(new Vector(reverseRotation, startForce));
		} else if ((actualRotation == reverseRotation || actualRotation + 1 == reverseRotation || actualRotation - 1 == reverseRotation)
				&& this.getSpeed() < maxSpeed) {
			this.accelerate(1 + accelerationDelta);
		} else {
			this.accelerate(1 - accelerationDelta);
		}

	}

	@Override
	protected void turnRight() {
		int actualRotation = getActualRotation();
		int turnSpeed = 1;

		if (inRange(actualRotation)) {
				this.getMovement().setDirection(this.getMovement().getDirection() + turnSpeed);
				this.setRotation(this.getRotation() + turnSpeed);
		}
	}

	@Override
	protected void turnLeft() {
		int actualRotation = getActualRotation();
		int turnSpeed = 1;

		if (inRange(actualRotation)) {
				this.getMovement().setDirection(this.getMovement().getDirection() - turnSpeed);
				this.setRotation(this.getRotation() - turnSpeed);
		}

	}

	private void adjustAngle() {
		int threshold = 20;
		int actualRotation = this.getMovement().getDirection() % circle;
		if (actualRotation < 0) {
			actualRotation += circle;
		}

		if (!inRange(this.getRotation())) {
			if ((circle - threshold < leftTurnMax && leftTurnMax < circle) || (0 <= leftTurnMax && leftTurnMax < threshold)) {
				if (rightTurnMax < this.getRotation() && this.getRotation() < rightTurnMax + threshold) {
					this.setRotation(rightTurnMax);
					if (reversing()) {
						this.getMovement().setDirection(getReverse(this.getRotation()));
					} else {
						this.getMovement().setDirection(this.getRotation());
					}
				} else if ((circle - threshold < this.getRotation() && this.getRotation() < circle)
						|| (0 <= this.getRotation() && this.getRotation() < threshold)) {
					this.setRotation(leftTurnMax);
					if (reversing()) {
						this.getMovement().setDirection(getReverse(this.getRotation()));
					} else {
						this.getMovement().setDirection(this.getRotation());
					}
				}
			} else {
				if (leftTurnMax - threshold < this.getRotation() && this.getRotation() < leftTurnMax) {
					this.setRotation(leftTurnMax);
					if (reversing()) {
						this.getMovement().setDirection(getReverse(this.getRotation()));
					} else {
						this.getMovement().setDirection(this.getRotation());
					}
				} else if (rightTurnMax < this.getRotation() && this.getRotation() < rightTurnMax + threshold) {
					this.setRotation(rightTurnMax);
					if (reversing()) {
						this.getMovement().setDirection(getReverse(this.getRotation()));
					} else {
						this.getMovement().setDirection(this.getRotation());
					}
				}
			}
			if ((circle - threshold < rightTurnMax && rightTurnMax < circle) || (0 <= rightTurnMax && rightTurnMax < threshold)) {
				if (leftTurnMax - threshold < this.getRotation() && this.getRotation() < leftTurnMax) {
					this.setRotation(leftTurnMax);
					if (reversing()) {
						this.getMovement().setDirection(getReverse(this.getRotation()));
					} else {
						this.getMovement().setDirection(this.getRotation());
					}
				} else if ((circle - threshold < this.getRotation() && this.getRotation() < circle)
						|| (0 <= this.getRotation() && this.getRotation() < threshold)) {
					this.setRotation(rightTurnMax);
					if (reversing()) {
						this.getMovement().setDirection(getReverse(this.getRotation()));
					} else {
						this.getMovement().setDirection(this.getRotation());
					}
				}
			} else {
				if (leftTurnMax - threshold < this.getRotation() && this.getRotation() < leftTurnMax) {
					this.setRotation(leftTurnMax);
					if (reversing()) {
						this.getMovement().setDirection(getReverse(this.getRotation()));
					} else {
						this.getMovement().setDirection(this.getRotation());
					}
				} else if (rightTurnMax < this.getRotation() && this.getRotation() < rightTurnMax + threshold) {
					this.setRotation(rightTurnMax);
					if (reversing()) {
						this.getMovement().setDirection(getReverse(this.getRotation()));
					} else {
						this.getMovement().setDirection(this.getRotation());
					}
				}
			}

		}
	}

	public boolean reversing() {
		int threshold = 20;
		int reverseRotation = this.getRotation() > 179 ? this.getRotation() - 180 : this.getRotation() + 180;

		int actualRotation = this.getMovement().getDirection() % circle;
		if (actualRotation < 0) {
			actualRotation += circle;
		}
		return (actualRotation - threshold < reverseRotation && reverseRotation < actualRotation + threshold)
				|| (actualRotation - threshold + circle < reverseRotation && reverseRotation < actualRotation + circle + threshold)
				|| (actualRotation - threshold - circle < reverseRotation && reverseRotation < actualRotation + threshold - circle);
	}

	private void updateBackTireDirection() {
		List<BackTire> backTire = this.getObjectsInRange(110, BackTire.class);
		this.backTireRotation = backTire.get(0).getRotation();
	}

	private void updateTurnRadius() {
		leftTurnMax = backTireRotation - turnRadius;
		rightTurnMax = backTireRotation + turnRadius;
		if (leftTurnMax < 0) {
			leftTurnMax += circle;
		}
		if (rightTurnMax > 359) {
			rightTurnMax -= circle;
		}
	}

	private int getReverse(int rotation) {
		return rotation > 179 ? rotation - 180 : rotation + 180;
	}

	private boolean inRange(int rotation) {
		boolean inRange = false;
		if ((circle - turnRadius <= backTireRotation && backTireRotation < circle) || (0 <= backTireRotation && backTireRotation <= turnRadius)) {
			if (leftTurnMax <= this.getRotation() && this.getRotation() < circle || 0 <= this.getRotation() && this.getRotation() <= rightTurnMax) {
				inRange = true;
			}
		} else {
			if (leftTurnMax <= this.getRotation() && this.getRotation() <= rightTurnMax) {
				inRange = true;
			}
		}
		return inRange;
	}

	private int getActualRotation() {
		int actualRotation = this.getMovement().getDirection() % circle;
		if (actualRotation < 0) {
			actualRotation += circle;
		}
		return actualRotation;
	}

	@Override
	protected void adjustSpeed() {
		this.accelerate(.99);
		
	}

}
