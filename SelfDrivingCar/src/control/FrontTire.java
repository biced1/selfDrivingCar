package control;

import greenfootAdditions.SmoothMover;
import greenfootAdditions.Vector;

public class FrontTire extends Tire {

	private final int turnRadius = 45;
	private int turnSpeed = 3;
	private int backTireRotation = 0;
	private SmoothMover backTire;
	private int rightTurnMax = turnRadius;
	private int leftTurnMax = -turnRadius;
	private int turn = 0;
	private double accelerationDelta = .05;
	private double startForce = .5;
	private static int circle = 360;

	public FrontTire(SmoothMover backTire){
		this.backTire = backTire;
	}
	
	@Override
	public void act() {
		this.updateBackTireDirection();
		this.updateTurnRadius();
		this.setAngle();
		this.adjustAngle();
		super.act();
	}

	@Override
	public void brake() {
		this.accelerate(.96);
	}

	@Override
	public void accelerate() {
		int actualRotation = getActualRotation(this.getMovement()
				.getDirection());

		if (this.getSpeed() == 0) {
			this.addForce(new Vector(
					getActualRotation(backTireRotation + turn), startForce));
		} else if ((actualRotation == this.getRotation()
				|| actualRotation + 1 == this.getRotation() || actualRotation - 1 == this
				.getRotation()) && this.getSpeed() < maxSpeed) {
			this.accelerate(1 + accelerationDelta);
		} else {
			this.accelerate(1 - accelerationDelta);
		}
	}

	@Override
	public void reverse() {
		if (this.getSpeed() == 0) {
			this.addForce(new Vector(getActualRotation(backTireRotation + 180
					+ turn), startForce));
		} else if (reversing() && this.getSpeed() < maxSpeed) {
			this.accelerate(1 + accelerationDelta);
		} else {
			this.accelerate(1 - accelerationDelta);
		}

	}

	@Override
	public void turnRight() {
		if (turn < turnRadius)
			turn += turnSpeed;
	}

	@Override
	public void turnLeft() {
		if (turn > -turnRadius)
			turn -= turnSpeed;
	}

	private void setAngle() {
		if (reversing()) {
			this.getMovement().setDirection(
					getActualRotation(backTireRotation + 180 + turn));
		} else {
			this.getMovement().setDirection(
					getActualRotation(backTireRotation + turn));
		}
		this.setRotation(getActualRotation(backTireRotation + turn));
	}

	private void adjustAngle() {
		int threshold = 20;
		int actualRotation = this.getMovement().getDirection() % circle;
		if (actualRotation < 0) {
			actualRotation += circle;
		}

		if (!inRange(this.getRotation())) {
			if ((circle - threshold < leftTurnMax && leftTurnMax < circle)
					|| (0 <= leftTurnMax && leftTurnMax < threshold)) {
				if (rightTurnMax < this.getRotation()
						&& this.getRotation() < rightTurnMax + threshold) {
					this.setRotation(rightTurnMax);
					if (reversing()) {
						this.getMovement().setDirection(
								getReverse(this.getRotation()));
					} else {
						this.getMovement().setDirection(this.getRotation());
					}
				} else if ((circle - threshold < this.getRotation() && this
						.getRotation() < circle)
						|| (0 <= this.getRotation() && this.getRotation() < threshold)) {
					this.setRotation(leftTurnMax);
					if (reversing()) {
						this.getMovement().setDirection(
								getReverse(this.getRotation()));
					} else {
						this.getMovement().setDirection(this.getRotation());
					}
				}
			} else {
				if (leftTurnMax - threshold < this.getRotation()
						&& this.getRotation() < leftTurnMax) {
					this.setRotation(leftTurnMax);
					if (reversing()) {
						this.getMovement().setDirection(
								getReverse(this.getRotation()));
					} else {
						this.getMovement().setDirection(this.getRotation());
					}
				} else if (rightTurnMax < this.getRotation()
						&& this.getRotation() < rightTurnMax + threshold) {
					this.setRotation(rightTurnMax);
					if (reversing()) {
						this.getMovement().setDirection(
								getReverse(this.getRotation()));
					} else {
						this.getMovement().setDirection(this.getRotation());
					}
				}
			}
			if ((circle - threshold < rightTurnMax && rightTurnMax < circle)
					|| (0 <= rightTurnMax && rightTurnMax < threshold)) {
				if (leftTurnMax - threshold < this.getRotation()
						&& this.getRotation() < leftTurnMax) {
					this.setRotation(leftTurnMax);
					if (reversing()) {
						this.getMovement().setDirection(
								getReverse(this.getRotation()));
					} else {
						this.getMovement().setDirection(this.getRotation());
					}
				} else if ((circle - threshold < this.getRotation() && this
						.getRotation() < circle)
						|| (0 <= this.getRotation() && this.getRotation() < threshold)) {
					this.setRotation(rightTurnMax);
					if (reversing()) {
						this.getMovement().setDirection(
								getReverse(this.getRotation()));
					} else {
						this.getMovement().setDirection(this.getRotation());
					}
				}
			} else {
				if (leftTurnMax - threshold < this.getRotation()
						&& this.getRotation() < leftTurnMax) {
					this.setRotation(leftTurnMax);
					if (reversing()) {
						this.getMovement().setDirection(
								getReverse(this.getRotation()));
					} else {
						this.getMovement().setDirection(this.getRotation());
					}
				} else if (rightTurnMax < this.getRotation()
						&& this.getRotation() < rightTurnMax + threshold) {
					this.setRotation(rightTurnMax);
					if (reversing()) {
						this.getMovement().setDirection(
								getReverse(this.getRotation()));
					} else {
						this.getMovement().setDirection(this.getRotation());
					}
				}
			}

		}
	}

	public boolean reversing() {
		int threshold = 20;
		int reverseRotation = this.getRotation() > 179 ? this.getRotation() - 180
				: this.getRotation() + 180;

		int actualRotation = this.getMovement().getDirection() % circle;
		if (actualRotation < 0) {
			actualRotation += circle;
		}
		return (actualRotation - threshold < reverseRotation && reverseRotation < actualRotation
				+ threshold)
				|| (actualRotation - threshold + circle < reverseRotation && reverseRotation < actualRotation
						+ circle + threshold)
				|| (actualRotation - threshold - circle < reverseRotation && reverseRotation < actualRotation
						+ threshold - circle);
	}

	private void updateBackTireDirection() {
			this.backTireRotation = backTire.getRotation();
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
		if ((circle - turnRadius <= backTireRotation && backTireRotation < circle)
				|| (0 <= backTireRotation && backTireRotation <= turnRadius)) {
			if (leftTurnMax <= this.getRotation()
					&& this.getRotation() < circle || 0 <= this.getRotation()
					&& this.getRotation() <= rightTurnMax) {
				inRange = true;
			}
		} else {
			if (leftTurnMax <= this.getRotation()
					&& this.getRotation() <= rightTurnMax) {
				inRange = true;
			}
		}
		return inRange;
	}

	private int getActualRotation(int rotation) {
		int actualRotation = rotation % circle;
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
