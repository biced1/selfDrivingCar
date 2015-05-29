package model;

import java.util.List;

public class Directions {
	private List<Step> steps;
	private DirectionsIterator iterator;
	
	public Directions(List<Step> steps){
		this.steps = steps;
		iterator = new DirectionsIterator(steps);
	}
	
	public DirectionsIterator iterator(){
		return iterator;
	}

	public List<Step> getSteps() {
		return steps;
	}	
	
	public int size(){
		return steps.size();
	}
}
