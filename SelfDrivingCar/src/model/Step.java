package model;

public class Step {
	private final Coordinate start;
	private final Coordinate end;
	private final StepCommand command;
	private final String textInstructions;

	public Step(Coordinate start, Coordinate end, StepCommand command, String textInstructions) {
		this.start = start;
		this.end = end;
		this.command = command;
		this.textInstructions = textInstructions;
	}

	public Coordinate getStart() {
		return start;
	}

	public Coordinate getEnd() {
		return end;
	}

	public StepCommand getCommand() {
		return command;
	}

	public String getTextInstructions() {
		return textInstructions;
	}

	@Override
	public String toString() {
		return "Start Coordinate " + start + "\nEnd Coordinate " + end + "\nCommand: " + command + "\nText Instructions: " + textInstructions;
	}
}
