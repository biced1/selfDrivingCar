package control;

import greenfootAdditions.Vector;

import java.util.List;

public class BackTire extends Tire {

	@Override
	public void act() {
		int carLength = 70;
		super.act();
		List<FrontTire> frontTire = this.getObjectsInRange(110, FrontTire.class);
		this.turnTowards(frontTire.get(0).getX(), frontTire.get(0).getY());
		this.getMovement().setDirection(this.getRotation());
		double distanceBetween = Math.sqrt(Math.pow(frontTire.get(0).getExactY() - this.getExactY(), 2)
				+ Math.pow(frontTire.get(0).getExactX() - this.getExactX(), 2));
		if (distanceBetween > carLength) {
			this.getMovement().setNeutral();
			this.addForce(new Vector(this.getRotation(), frontTire.get(0).getSpeed() + distanceBetween - carLength));
			// this.move((int)frontTire.get(0).getSpeed() + 1);
		} else if (distanceBetween < carLength) {
			this.getMovement().setNeutral();
			this.addForce(new Vector(this.getRotation(), frontTire.get(0).getSpeed() - (carLength - distanceBetween)));
		}
	}

	@Override
	protected void brake() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void accelerate() {

	}

	@Override
	protected void reverse() {

	}

	@Override
	protected void turnRight() {

	}

	@Override
	protected void turnLeft() {

	}

	@Override
	protected void adjustSpeed() {
		// TODO Auto-generated method stub
		
	}

}
