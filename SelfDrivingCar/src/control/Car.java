package control;

import greenfootAdditions.SmoothMover;

import java.util.ArrayList;
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
		traceRays();
		setPosition();
		if (this.isTouching(Map.class)) {
			this.setBlue();
		} else {
			this.setRed();
		}
		if (this.getFront().getSpeed() < 3) {
			this.getFront().accelerate();
		}
		double leftWallSlope = averageSlope(getRaysBetween(255, 285));
		double rightWallSlope = averageSlope(getRaysBetween(75, 105));
		double roadSlope = Math.atan((leftWallSlope + rightWallSlope) / 2)
				* 180 / Math.PI;
		roadSlope = slopeToDegrees(roadSlope);
		double leftMinDistance = getLeftMinDistance();
		double rightMinDistance = getRightMinDistance();

		int frontRotation = this.front.getRotation();		

		int closestDegree = closestDegree(frontRotation, (int)roadSlope, (int)getReflection(roadSlope));
		if (leftMinDistance < rightMinDistance * 2) {
			if (counterClockwiseDegreesAway(frontRotation, closestDegree) <= 3 || clockwiseDegreesAway(frontRotation, closestDegree) < 180) {
				this.front.turnRight();
			} else {
				straightenOut();
			}
		} else if (rightMinDistance* 2 < leftMinDistance) {
			if (clockwiseDegreesAway(frontRotation, closestDegree) <= 3 || counterClockwiseDegreesAway(frontRotation, closestDegree) < 180) {
				this.front.turnLeft();
			} else {
				straightenOut();
			}
		}

	}
	
	private double getReflection(double degree){
		double reflected = degree;
		if(0 <= degree && degree <= 90){
			reflected += 180;
		} if(270 <= degree && degree < 360){
			reflected -= 180;
		}
		
		return reflected;
	}
	
	private int closestDegree(int origin, int degree1, int degree2){
		int closestDegree = degree1;
		int degree1Clockwise = clockwiseDegreesAway(origin, degree1);
		int degree1Counterclock = counterClockwiseDegreesAway(origin, degree1);
		int degree2Clockwise = clockwiseDegreesAway(origin, degree2);
		int degree2Counterclock = counterClockwiseDegreesAway(origin, degree2);
		
		if(degree2Clockwise < degree1Clockwise && degree2Clockwise < degree1Counterclock){
			closestDegree = degree2;
		}
		if(degree2Counterclock < degree1Clockwise && degree2Counterclock < degree1Counterclock){
			closestDegree = degree2;
		}
			
		return closestDegree;
	}

	private int slopeToDegrees(double slope) {
		int degree = 0;
		if (Double.isNaN(slope)) {
			degree = 90;
		} else if (Double.isInfinite(slope)) {
			degree = 0;
		} else if ((int)slope < 0) {
			degree = (int) slope + 360;
		} else {
			degree = (int) slope;
		}

		return degree;
	}

	private void traceRays() {
		for (Ray r : rays) {
			r.reset(this.getExactX(), this.getExactY(), this.getRotation(),
					this.getFront().getMovement().getLength());
			while (!r.isDistanceReached() && !r.hitCurb()) {
				r.step();
			}
		}
	}

	private void pathOfLeastResistance() {
		int bestRotation = getBestDirection();
		if (clockwiseDegreesAway(this.getRotation(), bestRotation) < counterClockwiseDegreesAway(
				this.getRotation(), bestRotation)) {
			this.front.turnRight();
		} else if (clockwiseDegreesAway(this.getRotation(), bestRotation) > counterClockwiseDegreesAway(
				this.getRotation(), bestRotation)) {
			this.front.turnLeft();
		} else {
			straightenOut();
		}
	}

	private void straightenOut() {
		if (clockwiseDegreesAway(this.getRotation(), this.front.getRotation()) < 50
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

	private double averageSlope(List<Ray> rays) {
		Ray current = rays.get(0);
		double totalY = 0;
		double totalX = 0;

		for (int x = 1; x < rays.size() - 1; x++) {
				totalY += rays.get(x).getExactY() - current.getExactY() ;
				totalX += rays.get(x).getExactX() - current.getExactX();
				current = rays.get(x);
		}

		return totalY/totalX;
	}

	private double getSlope(SmoothMover mover1, SmoothMover mover2) {
		return (mover1.getExactY() - mover2.getExactY())
				/ (mover1.getExactX() - mover2.getExactX());
	}

	private double getLeftMinDistance() {
		double leftDistance = 1000;
		List<Ray> leftRays = getRaysBetween(270, 270);
		for (Ray r : leftRays) {
			double distance = r.getDistance();
			if (distance < leftDistance) {
				leftDistance = distance;
			}
		}
		return leftDistance;
	}

	private double getRightMinDistance() {
		double rightDistance = 1000;
		List<Ray> rightRays = getRaysBetween(90, 90);
		for (Ray r : rightRays) {
			double distance = r.getDistance();
			if (distance < rightDistance) {
				rightDistance = distance;
			}
		}
		return rightDistance;
	}

	private List<Ray> getRaysBetween(int offset1, int offset2) {
		List<Ray> raysBetween = new ArrayList<Ray>();
		for (int x = 0; x < rays.size(); x++) {
			if (offset1 <= rays.get(x).getOffset()
					&& rays.get(x).getOffset() <= offset2) {
				raysBetween.add(rays.get(x));
			}
		}
		return raysBetween;
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
