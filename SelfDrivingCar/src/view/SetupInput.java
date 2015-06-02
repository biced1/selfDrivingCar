package view;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SetupInput {
	private JTextField startLatitudeField = new JTextField();
	private JTextField startLongitudeField = new JTextField();
	private JTextField endLatitudeField = new JTextField();
	private JTextField endLongitudeField = new JTextField();
	
	private String error = "You entered an invalid number. Try again.";

	private double startLatitude = 40.557493;
	private double startLongitude = -112.312538;
	private double endLatitude = 40.565432;
	private double endLongitude = -112.301852;

	Object[] message = { "Start Latitude:", startLatitudeField, "Start Longitude:", startLongitudeField, "End Latitude:", endLatitudeField, "End Longitude:",
			endLongitudeField };

	public void displayInputs() {
		startLatitudeField.setText("40.557493");
		startLongitudeField.setText("-112.312538");
		endLatitudeField.setText("40.565432");
		endLongitudeField.setText("-112.301852");
		boolean valid = false;
		while (!valid) {
			JOptionPane.showMessageDialog(null, message);
			try {
				setLocations();
				valid = true;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, error);
			}
		}
	}
	
	public static void displayDownloading(){
		JOptionPane.showMessageDialog(null, "Your map is now downloading (it might take a few minutes).");
	}

	private void setLocations() {
		setStartLatitude(startLatitudeField.getText());
		setStartLongitude(startLongitudeField.getText());
		setEndLatitude(endLatitudeField.getText());
		setEndLongitude(endLongitudeField.getText());
	}

	private void setStartLatitude(String testStartLatitude) {
		this.startLatitude = Double.parseDouble(testStartLatitude);
	}

	private void setStartLongitude(String testStartLongitude) {
		this.startLongitude = Double.parseDouble(testStartLongitude);
	}

	private void setEndLatitude(String testEndLatitude) {
		this.endLatitude = Double.parseDouble(testEndLatitude);
	}

	private void setEndLongitude(String testEndLongitude) {
		this.endLongitude = Double.parseDouble(testEndLongitude);
	}

	public double getStartLatitude() {
		return this.startLatitude;
	}

	public double getStartLongitude() {
		return this.startLongitude;
	}

	public double getEndLatitude() {
		return this.endLatitude;
	}

	public double getEndLongitude() {
		return this.endLongitude;
	}
}
