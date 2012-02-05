package me.righi.backburner.svg;


public class Point {

	public Double x;

	public Double y;

	public Point() {

	}

	public Point(Double x, Double y) {
		this.x = x;
		this.y = y;
	}

	public Point(Integer x, Integer y) {
		this.x = new Double(x);
		this.y = new Double(y);
	}

	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public boolean isLeftOf(Point point) {
		return this.x < point.x;
	}

	public boolean isRightOf(Point point) {
		return this.x > point.x;
	}

	public boolean isAbove(Point point) {
		return this.y > point.y;
	}

	public boolean isBelow(Point point) {
		return this.y < point.y;
	}

	public void shift(double deltaX, double deltaY) {
		this.x += deltaX;
		this.y += deltaY;
	}

	public double distance(Point point) {
		double xDiff = (this.x - point.x);
		double yDiff = (this.y - point.y);
		return Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
	}

	public Point clone() {
		return new Point(x, y);
	}

	@Override
	public String toString() {
		return this.x + "," + this.y;
	}

}
