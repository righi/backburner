package me.righi.backburner.svg;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsolutePointCommand extends SVGCommand {

	protected AbsolutePointCommand(CommandType type, String path) {
		super(type, path);
	}

	protected AbsolutePointCommand(CommandType type, List<Point> points) {
		super(type, points);
	}

	@Override
	public List<Point> getPoints() {
		List<Point> points = new ArrayList<Point>();
		for (int i = 0; i < numbers.size(); i += 2) {
			Double x = numbers.get(i);
			Double y = numbers.get(i + 1);
			points.add(new Point(x, y));
		}
		return points;
	}

	@Override
	public void shift(double deltaX, double deltaY) {
		for (int i = 0; i < numbers.size(); i += 2) {
			Double x = numbers.get(i);
			Double y = numbers.get(i + 1);
			x += deltaX;
			y += deltaY;
			numbers.set(i, x);
			numbers.set(i + 1, y);
		}
	}
	
	@Override
	public void scale(double ratio, Point center) {
		for (int i = 0; i < numbers.size(); i += 2) {
			Double x = numbers.get(i);
			Double y = numbers.get(i + 1);
			double xDiff = x - center.getX();
			xDiff *= ratio;
			
			double yDiff = y - center.getY();
			yDiff *= ratio;
			
			numbers.set(i, center.getX() + xDiff);
			numbers.set(i + 1, center.getY() + yDiff);
		}
	}
	
	@Override
	public void flipHoriz(double width) {
		for (int i = 0; i < numbers.size(); i += 2) {
			Double x = numbers.get(i);
			x = width - x;
			numbers.set(i, x);
		}
	}
	

	@Override
	public Point getEndPoint() {
		List<Point> points = this.getPoints();
		return points.get(points.size() - 1);
	}

	@Override
	public Point getStartPoint() {
		return this.getPoints().get(0);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.type.name() + " ");

		List<Point> points = this.getPoints();
		for (int i = 0; i < points.size(); i++) {
			Point point = points.get(i);
			sb.append(AbsolutePointCommand.NF.format(point.getX()) + "," + AbsolutePointCommand.NF.format(point.getY()));
			if (i < points.size() - 1) {
				sb.append(" ");
			}
		}
		return sb.toString();
	}

}
