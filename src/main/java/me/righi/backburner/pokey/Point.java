package me.righi.backburner.pokey;

import java.text.NumberFormat;

public class Point {

	public static NumberFormat NF = NumberFormat.getInstance();
	
	static {
		NF.setMinimumFractionDigits(0);
		NF.setMaximumFractionDigits(3);
	}

	
	public double x;
	public double y;

	public Point() {

	}

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Point(Point p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	// A convenience to make it easier to convert the JavaScript code to Java.
	public static Point create(double x, double y) {
		return new Point(x, y);
	}

	public Point(String xy) {
		String[] tokens = xy.split(",");
		this.x = Double.parseDouble(tokens[0]);
		this.y = Double.parseDouble(tokens[1]);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return NF.format(x) + "," + NF.format(y);
	}

	public double distance(Point point) {
		double xDiff = (this.x - point.x);
		double yDiff = (this.y - point.y);
		return Math.sqrt((xDiff * xDiff) + (yDiff * yDiff));
	}

	public Point midpoint(Point point) {
		return new Point((this.x + point.x) / 2.0, (this.y + point.y) / 2.0);
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
	
	public Point clone() {
		return new Point(x, y);
	}

	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
}
