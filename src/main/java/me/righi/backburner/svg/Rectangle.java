package me.righi.backburner.svg;

public class Rectangle {

	private double x;

	private double y;

	private double width;

	private double height;
	
	public Rectangle() {
		
	}

	public Rectangle(double minX, double minY, double width, double height) {
		this.x = minX;
		this.y = minY;
		this.width = width;
		this.height = height;
	}
	
	public java.awt.Rectangle getAwt() {
		return new java.awt.Rectangle((int) x, (int) y, (int) width, (int) height);
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

	public double getMinX() {
		return x;
	}

	public double getMaxX() {
		return x + width;
	}

	public double getMaxY() {
		return y + height;
	}

	public void setMinX(double minX) {
		this.x = minX;
	}

	public double getMinY() {
		return y;
	}

	public void setMinY(double minY) {
		this.y = minY;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}
	
	@Override
	public String toString() {
		return this.getClass().getName() + "[x=" + x + ",y=" + y + ",width=" + width + ",height=" + height + "]";
	}

}
