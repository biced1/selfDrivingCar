package control;

import greenfootAdditions.SmoothMover;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Car extends SmoothMover {
	private BackTire rearTire = new BackTire();
	private FrontTire frontTire = new FrontTire(rearTire);
	private List<Ray> rays;
	private final double maxSpeed = 3;
	private double speed = maxSpeed;
	private double minSpeed = 1;

	private double maxTurn = 3;
	private double gentleTurn = 2;

	private int halfCircle = 180;
	private int circle = 360;
	private int quarterCircle = 90;
	private int threeQuarterCircle = 270;

	private int leftRay = 270;
	private int rightRay = 90;
	private int rayThreshold = 40;
	private int frontRayRange = 30;

	private static int roadWidth = 110;

	private double previousCrosstrackError = 0;

	private String blueCarPath = "images/regular/blueCar.png";
	private String redCarPath = "images/regular/redCar.png";
	private int scale = 1;

	public Car(List<Ray> rays) {
		super();
		rearTire.setFrontTire(frontTire);
		this.rays = rays;
		setRed();
		setBlue();
	}

	public void setPosition() {
		double xCenter = (frontTire.getExactX() + rearTire.getExactX()) / 2;
		double yCenter = (frontTire.getExactY() + rearTire.getExactY()) / 2;

		this.setLocation(xCenter, yCenter);
		this.setRotation(rearTire.getRotation());
	}

	@Override
	public void act() {
		traceRays();
		setPosition();
		drive();

		List<Ray> rays = getRaysBetween(leftRay, leftRay + rayThreshold);
		for (Ray r : rays) {
			r.setColor(new Color(0, 0, 204));
		}
	}

	private void drive() {
		if (this.getFront().getSpeed() < speed) {
			this.getFront().accelerate();
		}

		if (inIntersection(leftRay - rayThreshold, leftRay + rayThreshold) || inIntersection(rightRay - rayThreshold, rightRay + rayThreshold)) {
			handleIntersection();
		} else {
			followRoad();
		}
	}

	private void handleIntersection() {
		int frontMinimumDistance = 125;

		if (getMaxDistance(getFrontRays(frontRayRange)) < frontMinimumDistance) {
			speed = minSpeed;
			if (inIntersection(rightRay - rayThreshold, rightRay + rayThreshold)) {
				System.out.println("right");
				this.frontTire.turnRight(maxTurn);
			} else if (inIntersection(leftRay - rayThreshold, leftRay + rayThreshold)) {
				System.out.println("left");
				this.frontTire.turnLeft(maxTurn);
			}
		} else {
			turnTowards(this.getRotation());
		}
	}

	private void followRoad() {
		int frontMinimumDistance = 125;
		int frontRayRange = 30;
		double laneOffset = 1.5;
		double crosstrackDegreeAdjust = 5;
		int toPercent = 100;

		double leftWallSlope = averageSlope(getRaysBetween(leftRay, leftRay + rayThreshold));
		double rightWallSlope = averageSlope(getRaysBetween(rightRay - rayThreshold, rightRay));
		double roadDegrees = slopeToDegrees((leftWallSlope + rightWallSlope) / 2);

		double leftMinDistance = getMinDistance(leftRay - rayThreshold, leftRay + rayThreshold) / laneOffset;
		double rightMinDistance = getMinDistance(rightRay - rayThreshold, rightRay + rayThreshold) * laneOffset;

		int frontRotation = this.frontTire.getRotation();
		roadDegrees = closestDegree(frontRotation, (int) roadDegrees, (int) getReflection(roadDegrees));

		double crosstrackError = rightMinDistance - leftMinDistance;
		double adjustDegrees = (crosstrackError / (roadWidth * crosstrackDegreeAdjust) * toPercent)
				- ((previousCrosstrackError - crosstrackError) / (roadWidth * crosstrackDegreeAdjust) * toPercent);

		double maxFrontDistance = getMaxDistance(getFrontRays(frontRayRange));
		if (maxFrontDistance < frontMinimumDistance) {
			speed = 0;
			this.frontTire.brake();
		} else {
			speed = maxSpeed;
			turnTowards(getActualRotation((int) (roadDegrees + adjustDegrees)));
		}

		previousCrosstrackError = crosstrackError;
	}

	private List<Ray> getFrontRays(int threshold) {
		List<Ray> frontRays = getRaysBetween(circle - threshold, circle);
		frontRays.addAll(getRaysBetween(0, threshold));
		return frontRays;
	}

	private double getMaxDistance(List<Ray> rays) {
		double maxDistance = 0;
		for (Ray r : rays) {
			if (r.getDistance() > maxDistance) {
				maxDistance = r.getDistance();
			}
		}
		return maxDistance;
	}

	private boolean inIntersection(int firstRay, int secondRay) {
		boolean inIntersection = false;
		List<Ray> leftRays = getRaysBetween(firstRay, secondRay);
		for (Ray r : leftRays) {
			if (r.isDistanceReached()) {
				inIntersection = true;
			}
		}
		return inIntersection;
	}

	private double getReflection(double degree) {
		double reflected = degree;
		if (0 <= degree && degree <= quarterCircle) {
			reflected += halfCircle;
		}
		if (threeQuarterCircle <= degree && degree < circle) {
			reflected -= halfCircle;
		}

		return reflected;
	}

	private int closestDegree(int origin, int degree1, int degree2) {
		int closestDegree = degree1;
		int degree1Clockwise = clockwiseDegreesAway(origin, degree1);
		int degree1Counterclock = counterClockwiseDegreesAway(origin, degree1);
		int degree2Clockwise = clockwiseDegreesAway(origin, degree2);
		int degree2Counterclock = counterClockwiseDegreesAway(origin, degree2);

		if (degree2Clockwise < degree1Clockwise && degree2Clockwise < degree1Counterclock) {
			closestDegree = degree2;
		}
		if (degree2Counterclock < degree1Clockwise && degree2Counterclock < degree1Counterclock) {
			closestDegree = degree2;
		}

		return closestDegree;
	}

	private int slopeToDegrees(double slope) {
		slope = Math.atan(slope) * halfCircle / Math.PI;
		int degree;
		if (Double.isNaN(slope)) {
			degree = quarterCircle;
		} else if (Double.isInfinite(slope)) {
			degree = 0;
		} else if ((int) slope < 0) {
			degree = (int) slope + circle;
		} else {
			degree = (int) slope;
		}

		return degree;
	}

	private void traceRays() {
		for (Ray r : rays) {
			r.reset(this.getExactX(), this.getExactY(), this.getRotation(), this.getFront().getMovement().getLength());
			while (!r.isDistanceReached() && !r.hitCurb()) {
				r.step();
			}
		}
	}

	private void turnTowards(int rotation) {
		if (clockwiseDegreesAway(rotation, this.frontTire.getRotation()) <= halfCircle && clockwiseDegreesAway(rotation, this.frontTire.getRotation()) > 0) {
			this.frontTire.turnLeft(gentleTurn);
		} else if (counterClockwiseDegreesAway(rotation, this.frontTire.getRotation()) < halfCircle
				&& counterClockwiseDegreesAway(rotation, this.frontTire.getRotation()) > 0) {
			this.frontTire.turnRight(gentleTurn);
		}
	}

	private int clockwiseDegreesAway(int rotationFacing, int rotationToTurn) {
		int degreesAway = 0;
		while (rotationFacing != rotationToTurn) {
			rotationFacing++;
			if (rotationFacing >= circle) {
				rotationFacing -= circle;
			}
			degreesAway++;
		}
		return degreesAway;
	}

	private int counterClockwiseDegreesAway(int rotationFacing, int rotationToTurn) {
		int degreesAway = 0;
		while (rotationFacing != rotationToTurn) {
			rotationFacing--;
			if (rotationFacing < 0) {
				rotationFacing += circle;
			}
			degreesAway++;
		}
		return degreesAway;
	}

	private double averageSlope(List<Ray> rays) {
		Ray current = rays.get(0);
		double totalY = 0;
		double totalX = 0;

		for (int x = 1; x < rays.size(); x++) {
			totalY += rays.get(x).getExactY() - current.getExactY();
			totalX += rays.get(x).getExactX() - current.getExactX();
			current = rays.get(x);
		}

		return totalY / totalX;
	}

	private double getMinDistance(int firstRay, int lastRay) {
		double leftDistance = Integer.MAX_VALUE;
		List<Ray> leftRays = getRaysBetween(firstRay, lastRay);
		for (Ray r : leftRays) {
			double distance = r.getDistance();
			if (distance < leftDistance) {
				leftDistance = distance;
			}
		}
		return leftDistance;
	}

	private List<Ray> getRaysBetween(int offset1, int offset2) {
		List<Ray> raysBetween = new ArrayList<Ray>();
		for (int x = 0; x < rays.size(); x++) {
			if (offset1 <= rays.get(x).getOffset() && rays.get(x).getOffset() <= offset2) {
				raysBetween.add(rays.get(x));
			}
		}
		return raysBetween;
	}

	private int getActualRotation(int rotation) {
		int actualRotation = rotation % circle;
		if (actualRotation < 0) {
			actualRotation += circle;
		}
		return actualRotation;
	}

	private void setBlue() {
		this.setImage(blueCarPath);
		this.getImage().scale(this.getImage().getWidth() / scale, this.getImage().getHeight() / scale);
	}

	private void setRed() {
		this.setImage(redCarPath);
	}

	public FrontTire getFront() {
		return frontTire;
	}

	public SmoothMover getRear() {
		return rearTire;
	}
}
