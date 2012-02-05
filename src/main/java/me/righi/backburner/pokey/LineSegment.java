package me.righi.backburner.pokey;

public class LineSegment {

	private Point pointA;
	private Point pointB;

	public LineSegment(Point pointA, Point pointB) {
		this.setPointA(pointA);
		this.setPointB(pointB);
	}

	public LineSegment(Point pointA, double angle, double length) {
		double radian = angle * Math.PI / 180;
		LineSegment tempLine = new LineSegment(new Point(0, 0), new Point(Math.cos(radian), Math.sin(radian)));
		Point dv = tempLine.getDirectionVector();

		this.setPointA(pointA);
		this.setPointB(new Point(pointA.x + dv.x * length, pointA.y + dv.y * length));
	}

	public Point getPointA() {
		return pointA;
	}

	public void setPointA(Point pointA) {
		this.pointA = pointA;
	}

	public Point getPointB() {
		return pointB;
	}

	public void setPointB(Point pointB) {
		this.pointB = pointB;
	}

	public Point getPoint(double t) {
		if (t < 0 || t > 1) throw new IllegalArgumentException("Illegal value " + t + " for 't' in getPoint.  Value must be: 0>=t<=1");
		
		double m = this.getLength() * t;
		Point dv = this.getDirectionVector();
		double x = this.pointA.x + (m * dv.x);
		double y = this.pointA.y + (m * dv.y);
		return new Point(x,y);
	}
	
	public Double getSlope() {
		double yDiff = pointB.y - pointA.y;
		double xDiff = pointB.x - pointA.x;

		if (xDiff != 0) {
			return yDiff / xDiff;
		} else if (pointA.isBelow(pointB)) {
			return Double.POSITIVE_INFINITY;
		} else {
			return Double.NEGATIVE_INFINITY;
		}
	}

	public double getAngle() {
		double deltaY = this.pointB.y - this.pointA.y;
		double deltaX = this.pointB.x - this.pointA.x;
		double deg = 180 * Math.atan2(deltaY, deltaX) / Math.PI;
		if (deg < 0) {
			deg += 360;
		}
		return deg;
	}

	public double getLength() {
		return this.pointA.distance(this.pointB);
	}

	public double getMagnitude() {
		double slope = this.getSlope();

		if (Double.isInfinite(slope)) {
			return 0;
		} else {
			return Math.pow((1 + Math.pow(slope, 2)), 0.5);
		}
	}

	public Point getDirectionVector() {
		double slope = this.getSlope();
		if (slope == Double.POSITIVE_INFINITY) {
			return new Point(0, 1);
		} else if (slope == Double.NEGATIVE_INFINITY) {
			return new Point(0, -1);
		} else if (slope == 0) {
			return pointA.isLeftOf(pointB) ? new Point(1, 0) : new Point(-1, 0);
		} else {
			double magnitude = this.getMagnitude();
			double x = 1 / magnitude;
			double y = slope / magnitude;
			if (pointA.isRightOf(pointB)) {
				return new Point(-x, -y);
			} else {
				return new Point(x, y);
			}
		}
	}

}
