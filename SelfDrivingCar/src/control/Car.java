package control;

import greenfoot.GreenfootImage;
import greenfootAdditions.GreenfootImageHelp;
import greenfootAdditions.SmoothMover;

import java.util.ArrayList;
import java.util.List;

import model.Coordinate;
import model.Directions;
import model.Step;
import model.StepCommand;

public class Car extends SmoothMover {
	private BackTire rearTire;
	private FrontTire frontTire;

	private int tirePositionAdjust;

	private List<Ray> rays;
	private List<Ray> frontRays;
	private List<Ray> leftFrontRays;
	private List<Ray> rightFrontRays;

	private Directions currentDirections;
	private int currentStep = 0;

	private Coordinate currentPosition;

	private final double maxSpeed = 3;
	private double speed = maxSpeed;
	private double minSpeed = 1;

	private double maxTurn = 3;
	private double gentleTurn = 1;

	private int halfCircle = 180;
	private int circle = 360;
	private int quarterCircle = 90;
	private int threeQuarterCircle = 270;

	private int leftRay = 270;
	private int rightRay = 90;
	private int rayThreshold = 50;
	private int frontRayRange = 30;

	private static int roadWidth = 110;

	private double previousCrosstrackError = 0;

	private String blueCarPath = "images/regular/blueCar.png";
	private String redCarPath = "images/regular/redCar.png";
	private final double baseScale = 1;
	private double scale = 1;

	private final int feetPerMile = 5280;

	public Car(List<Ray> rays, Directions directions) {
		super();
		this.rays = rays;
		this.currentDirections = directions;
		setRed();
		setBlue();
		tirePositionAdjust = this.getImage().getWidth() / 3;
		rearTire = new BackTire(this.getImage().getWidth() - tirePositionAdjust);
		frontTire = new FrontTire(rearTire);
		rearTire.setFrontTire(frontTire);
		leftFrontRays = getRaysBetween(leftRay, leftRay + rayThreshold);
		rightFrontRays = getRaysBetween(rightRay - rayThreshold, rightRay);
		frontRays = getFrontRays(frontRayRange);
		
		System.out.println(directions.getSteps());
	}

	public void scaleCar(double scaleModifier) {
		this.scale = scaleModifier * baseScale;
		setBlue();
		tirePositionAdjust = this.getImage().getWidth() / 3;
		BackTire.setCarLength(this.getImage().getWidth() - tirePositionAdjust);

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

	}

	private void drive() {
		if (this.getFront().getSpeed() < speed) {
			this.getFront().accelerate();
		}
		int nearIntersection = 14;

		Step step = currentDirections.getSteps().get(currentStep);

		Coordinate origin = step.getStart();
		Coordinate destination = step.getEnd();
		double feetAwayFromDestination = calculateDistance(currentPosition.getLatitude(), currentPosition.getLongitude(), destination.getLatitude(),
				destination.getLongitude()) * feetPerMile;
		double feetAwayFromOrigin = calculateDistance(currentPosition.getLatitude(), currentPosition.getLongitude(), origin.getLatitude(),
				origin.getLongitude())
				* feetPerMile;
		if (feetAwayFromDestination < nearIntersection) {
			currentStep++;
			System.out.println("Step " + currentStep);
		}
		System.out.println("Destination " + feetAwayFromDestination);
		System.out.println("Origin " + feetAwayFromOrigin);
		if(feetAwayFromOrigin < nearIntersection){
			if (step.getCommand() == StepCommand.RIGHT) {
				System.out.println("turning right");
				turnRight();
			} else if(step.getCommand() == StepCommand.LEFT){
				System.out.println("turning left");
				turnLeft();
			}
		}
		followRoad();

	}

	private void handleIntersection() {
		int frontMinimumDistance = 300;
		if (getMaxDistance(getFrontRays(frontRayRange)) < frontMinimumDistance) {
			speed = minSpeed;
			if (inIntersection(rightRay - rayThreshold, rightRay + rayThreshold)) {
				this.frontTire.turnRight(maxTurn);
			} else if (inIntersection(leftRay - rayThreshold, leftRay + rayThreshold)) {
				this.frontTire.turnLeft(maxTurn);
			}
		} else {
			turnTowards(this.getRotation());
		}
	}

	private void turnRight() {
		turnTowards(getActualRotation(this.getRotation() + quarterCircle));
	}

	private void turnLeft() {
		turnTowards(getActualRotation(this.getRotation() - quarterCircle));
	}

	private void followRoad() {
		int frontMinimumDistance = 125;
		double laneOffset = 1.8;
		double crosstrackDegreeAdjust = 5;
		int toPercent = 100;

		double leftWallSlope = averageSlope(leftFrontRays);
		double rightWallSlope = averageSlope(rightFrontRays);
		double roadDegrees = slopeToDegrees((leftWallSlope + rightWallSlope) / 2);

		double leftMinDistance = getMinDistance(leftRay, leftRay) / laneOffset;
		double rightMinDistance = getMinDistance(rightRay, rightRay) * laneOffset;

		int frontRotation = this.frontTire.getRotation();
		roadDegrees = closestDegree(frontRotation, (int) roadDegrees, (int) getReflection(roadDegrees));

		double crosstrackError = rightMinDistance - leftMinDistance;
		double adjustDegrees = (crosstrackError / (roadWidth * crosstrackDegreeAdjust) * toPercent)
				- ((previousCrosstrackError - crosstrackError) / (roadWidth * crosstrackDegreeAdjust) * toPercent);

		speed = maxSpeed;
		turnTowards(getActualRotation((int) (roadDegrees + adjustDegrees)));

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

		for (int x = 1; x < rays.size(); x += 3) {
			Ray checking = rays.get(x);
			if (!checking.isDistanceReached() || !current.isDistanceReached()) {
				totalY += checking.getExactY() - current.getExactY();
				totalX += checking.getExactX() - current.getExactX();
			}
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

	private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
		double theta = lon1 - lon2;
		double dist = Math.sin(degreesToRadians(lat1)) * Math.sin(degreesToRadians(lat2)) + Math.cos(degreesToRadians(lat1)) * Math.cos(degreesToRadians(lat2))
				* Math.cos(degreesToRadians(theta));
		dist = Math.acos(dist);
		dist = radiansToDegrees(dist);
		dist = dist * 60 * 1.1515;
		return (dist);
	}

	private double radiansToDegrees(double rad) {
		return (rad * 180 / Math.PI);
	}

	private double degreesToRadians(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private void setBlue() {
		GreenfootImage image = new GreenfootImage(blueCarPath);
		this.setImage(GreenfootImageHelp.scale(image, (int) (image.getWidth() * scale), (int) (image.getHeight() * scale)));
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

	public double getScale() {
		return scale;
	}

	public void setScale(double scale) {
		this.scale = scale;
	}

	public Coordinate getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Coordinate currentPosition) {
		this.currentPosition = currentPosition;
	}

	public Directions getCurrentDirections() {
		return currentDirections;
	}

	public void setCurrentDirections(Directions currentDirections) {
		this.currentDirections = currentDirections;
	}

}
