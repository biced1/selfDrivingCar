package control;

import java.util.List;

import greenfootAdditions.Vector;

public class FrontTire extends Tire {

	private final int turnRadius = 45;
	private int backTireRotation = 0;
	private int rightTurnMax = 30;
	private int leftTurnMax = -30;

	@Override
	public void act() {
		this.updateBackTireDirection();
		this.updateTurnRadius();
		// this.adjustAngle();
		System.out.println(rightTurnMax + " " + leftTurnMax + " "
				+ backTireRotation + " " + this.getRotation());
		super.act();
	}

	@Override
	protected void brake() {
		this.accelerate(.96);

	}

	@Override
	protected void accelerate() {
		double delta = .04;
		int actualRotation = this.getMovement().getDirection() % 359;
		if (actualRotation < 0) {
			actualRotation += 360;
		}

		if (this.getSpeed() == 0) {
			this.addForce(new Vector(this.getRotation(), .6));
		} else if ((actualRotation == this.getRotation()
				|| actualRotation + 1 == this.getRotation() || actualRotation - 1 == this
				.getRotation()) && this.getSpeed() < maxSpeed) {
			this.accelerate(1 + delta);
		} else {
			this.accelerate(1 - delta);
		}
	}

	@Override
	protected void reverse() {
		double delta = .05;
		int reverseRotation = this.getRotation() > 179 ? this.getRotation() - 180
				: this.getRotation() + 180;

		int actualRotation = this.getMovement().getDirection() % 360;
		if (actualRotation < 0) {
			actualRotation += 360;
		}

		if (this.getSpeed() == 0) {
			this.addForce(new Vector(reverseRotation, .6));
		} else if ((actualRotation == reverseRotation
				|| actualRotation + 1 == reverseRotation || actualRotation - 1 == reverseRotation)
				&& this.getSpeed() < maxSpeed) {
			this.accelerate(1 + delta);
		} else {
			this.accelerate(1 - delta);
		}

	}

	@Override
	protected void turnRight() {
		int reverseRotation = this.getRotation() > 179 ? this.getRotation() - 180
				: this.getRotation() + 180;

		int actualRotation = this.getMovement().getDirection() % 360;
		if (actualRotation < 0) {
			actualRotation += 360;
		}

		if ((backTireRotation >= 360 - turnRadius && backTireRotation < 360)
				|| (backTireRotation >= 0 && backTireRotation <= turnRadius)) {
			if (leftTurnMax <= this.getRotation() && this.getRotation() < 360
					|| 0 <= this.getRotation()
					&& this.getRotation() <= rightTurnMax) {
				if (actualRotation == reverseRotation) {
					if (this.getRotation() != leftTurnMax) {
						this.getMovement().setDirection(
								this.getMovement().getDirection() - 1);
						this.setRotation(this.getRotation() - 1);
					}
				} else {
					if (this.getRotation() != rightTurnMax) {
						this.getMovement().setDirection(
								this.getMovement().getDirection() + 1);
						this.setRotation(this.getRotation() + 1);
					}
				}
			}
		} else {
			if (leftTurnMax <= this.getRotation()
					&& this.getRotation() <= rightTurnMax) {
				if (actualRotation == reverseRotation) {
					if (this.getRotation() != leftTurnMax) {
						this.getMovement().setDirection(
								this.getMovement().getDirection() - 1);
						this.setRotation(this.getRotation() - 1);
					}
				} else {
					if (this.getRotation() != rightTurnMax) {
						this.getMovement().setDirection(
								this.getMovement().getDirection() + 1);
						this.setRotation(this.getRotation() + 1);
					}
				}
			}
		}
	}

	@Override
	protected void turnLeft() {
		int reverseRotation = this.getRotation() > 179 ? this.getRotation() - 180
				: this.getRotation() + 180;

		int actualRotation = this.getMovement().getDirection() % 360;
		if (actualRotation < 0) {
			actualRotation += 360;
		}

		if ((backTireRotation >= 360 - turnRadius && backTireRotation < 360)
				|| (backTireRotation >= 0 && backTireRotation <= turnRadius)) {
			if (leftTurnMax <= this.getRotation() && this.getRotation() < 360
					|| 0 <= this.getRotation()
					&& this.getRotation() <= rightTurnMax) {
				if (actualRotation == reverseRotation) {
					if (this.getRotation() != rightTurnMax) {
						this.getMovement().setDirection(
								this.getMovement().getDirection() + 1);
						this.setRotation(this.getRotation() + 1);
					}
				} else {
					if (this.getRotation() != leftTurnMax) {
						this.getMovement().setDirection(
								this.getMovement().getDirection() - 1);
						this.setRotation(this.getRotation() - 1);
					}
				}
			}
		} else {
			if (leftTurnMax <= this.getRotation()
					&& this.getRotation() <= rightTurnMax) {
				if (actualRotation == reverseRotation) {
					if (this.getRotation() != rightTurnMax) {
						this.getMovement().setDirection(
								this.getMovement().getDirection() + 1);
						this.setRotation(this.getRotation() + 1);
					}
				} else {
					if (this.getRotation() != leftTurnMax) {
						this.getMovement().setDirection(
								this.getMovement().getDirection() - 1);
						this.setRotation(this.getRotation() - 1);
					}
				}
			}
		}
	}

	private void adjustAngle() {
		if (this.getRotation() > leftTurnMax
				&& this.getRotation() < rightTurnMax) {
			// that's okay
		} else if (this.getRotation() - leftTurnMax < 0
				&& this.getRotation() - leftTurnMax > -5) {
			System.out.println("this rotation < leftTurnMax");
			if (reversing()) {
				this.getMovement().setDirection(getReverse(leftTurnMax));
			} else {
				this.getMovement().setDirection(leftTurnMax);
			}
			this.setRotation(leftTurnMax);
		} else if (this.getRotation() - rightTurnMax > 0
				&& this.getRotation() - rightTurnMax < 5) {
			System.out.println("this rotation > rightTurnMax");
			if (reversing()) {
				this.getMovement().setDirection(getReverse(rightTurnMax));
			} else {
				this.getMovement().setDirection(rightTurnMax);
			}
			this.setRotation(rightTurnMax);
		}
	}

	public boolean reversing() {
		int reverseRotation = this.getRotation() > 179 ? this.getRotation() - 180
				: this.getRotation() + 180;

		int actualRotation = this.getMovement().getDirection() % 360;
		if (actualRotation < 0) {
			actualRotation += 360;
		}
		return reverseRotation == actualRotation;
	}

	private void updateBackTireDirection() {
		List<BackTire> backTire = this.getObjectsInRange(110, BackTire.class);
		this.backTireRotation = backTire.get(0).getRotation();
	}

	private void updateTurnRadius() {
		leftTurnMax = backTireRotation - turnRadius;
		rightTurnMax = backTireRotation + turnRadius;
		if (leftTurnMax < 0) {
			leftTurnMax += 360;
		}
		if (rightTurnMax > 359) {
			rightTurnMax -= 360;
		}
	}

	private int getReverse(int rotation) {
		return rotation > 179 ? rotation - 180 : rotation + 180;
	}

}
