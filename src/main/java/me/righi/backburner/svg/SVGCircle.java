package me.righi.backburner.svg;

public class SVGCircle extends SVGComponent {

	private Point point;

	private Double radius;

	public SVGCircle(String id, Point point, Double radius) {
		this.id = id;
		this.point = point;
		this.radius = radius;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double radius) {
		this.radius = radius;
	}

	@Override
	public void shift(double x, double y) {
		point.shift(x, y);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(point.x, point.y, point.x + radius, point.y + radius);
	}

	@Override
	public void scale(double ratio, Point center) {
		double x = point.getX();
		double y = point.getY();
		double xDiff = x - center.getX();
		xDiff *= ratio;

		double yDiff = y - center.getY();
		yDiff *= ratio;
		point.setX(center.getX() + xDiff);
		point.setY(center.getY() + yDiff);
	}

	@Override
	public String getXml() {
		String idStr = (id == null) ? "" : "id='" + id + "' ";
		return "<circle " + idStr + " r='" + radius + "' cx='" + point.getX() + "' cy='" + point.getY() + "' " + this.getStyle() + "/>";
	}
}
