package model;

public class Point {
	private long x;
	private long y;
	
	public Point(long x, long y){
		this.setX(x);
		this.setY(y);
	}

	public long getX() {
		return x;
	}

	public void setX(long x) {
		this.x = x;
	}

	public long getY() {
		return y;
	}

	public void setY(long y) {
		this.y = y;
	}
	
	@Override
	public String toString(){
		return x + " " + y;
	}
}
