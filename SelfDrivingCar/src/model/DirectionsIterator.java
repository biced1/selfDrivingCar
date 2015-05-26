package model;

import java.util.Iterator;
import java.util.List;

public class DirectionsIterator implements Iterator<Step> {
	private List<Step> steps;
	private int currentIndex = 0;
	
	public DirectionsIterator(List<Step> steps){
		this.steps = steps;
	}
	
	
	@Override
	public boolean hasNext() {
		return currentIndex < steps.size() - 1;
	}

	@Override
	public Step next() {
		Step current = steps.get(currentIndex);
		currentIndex++;
		return current;
	}

}
