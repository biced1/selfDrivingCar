package control;

import java.awt.Color;

public class ColorValidation {
	private static int invalidRedValue = 185;
	private static int redErrorThreshold = 20;
	private static int invalidBlueValue = 185;
	private static int blueErrorThreshold = 20;
	private static int invalidGreenValue = 185;
	private static int greenErrorThreshold = 20;
	
	public static boolean isRoadColor(Color testColor){
		return (!(isWithinThreshold(testColor.getRed(), invalidRedValue, redErrorThreshold)
					&& isWithinThreshold(testColor.getBlue(), invalidBlueValue, blueErrorThreshold) && isWithinThreshold(testColor.getGreen(),
						invalidGreenValue, greenErrorThreshold)));
	}
	
	private static boolean isWithinThreshold(int testValue, int prefferedValue, int threshold) {
		return prefferedValue - threshold <= testValue && testValue <= prefferedValue + threshold;
	}
}
