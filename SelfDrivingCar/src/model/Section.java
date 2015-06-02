package model;

public class Section {
	private int startOffset;
	private int endOffset;
	private int circle = 360;

	public Section(int startOffset, int endOffset) {
		this.setStartOffset(startOffset);
		this.setEndOffset(endOffset);
	}

	public int getStartOffset() {
		return startOffset;
	}

	public void setStartOffset(int startOffset) {
		this.startOffset = startOffset;
	}

	public int getEndOffset() {
		return endOffset;
	}

	public void setEndOffset(int endOffset) {
		this.endOffset = endOffset;
	}

	public int getLength() {
		boolean reachedEnd = false;
		int start = startOffset;
		int total = 0;
		while (!reachedEnd) {
			if (start == endOffset) {
				reachedEnd = true;
			} else {
				if (start >= circle) {
					start -= circle;
				}
				start++;
				total++;
			}
		}
		return total;
	}

	public int getOffsetAt(double percentage) {
		int length = getLength();
		int offset = -1;
		int destinationOffset = (int) (length * percentage);
		boolean reachedPercent = false;
		int start = startOffset;
		int stepsTaken = 0;
		while (!reachedPercent) {
			if (stepsTaken == destinationOffset) {
				offset = start;
				reachedPercent = true;
			} else {
				if (start >= circle) {
					start -= circle;
				}
				start++;
				stepsTaken++;
			}
		}
		return offset;
	}

	public boolean containsOffset(int offset) {
		boolean reachedEnd = false;
		boolean contains = false;
		int start = startOffset;
		while (!reachedEnd) {
			if (start == endOffset) {
				reachedEnd = true;
			} else if (start == offset) {
				reachedEnd = true;
				contains = true;
			} else {
				if (start >= circle) {
					start -= circle;
				}
				start++;
			}
		}
		return contains;
	}
}
