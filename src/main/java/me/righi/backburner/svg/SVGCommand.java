package me.righi.backburner.svg;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class SVGCommand {

	protected CommandType type;

	protected List<Double> numbers = new ArrayList<Double>();

	protected static NumberFormat NF;

	protected SVGCommand nextCommand;

	protected SVGCommand prevCommand;
	
	static {
		NF = new DecimalFormat();
		NF.setMaximumFractionDigits(2);
		NF.setMinimumFractionDigits(0);
		NF.setGroupingUsed(false);
	}

	protected SVGCommand(CommandType type, String path) {
		this.type = type;
		this.numbers = SVGUtil.numbers(path);
	}

	protected SVGCommand(CommandType type, List<Point> points) {
		this.type = type;
		for (Point point : points) {
			numbers.add(point.getX());
			numbers.add(point.getY());
		}
	}

	public static SVGCommand parse(String svg) {
		CommandType type = CommandType.parse(svg);
		try {
			return type.getClazz().getDeclaredConstructor(CommandType.class, String.class).newInstance(type, svg);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	protected static SVGCommand parse(CommandType type, List<Point> points) {
		try {
			return type.getClazz().getDeclaredConstructor(CommandType.class, List.class).newInstance(type, points);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public abstract void shift(double deltaX, double deltaY);
	
	public abstract void scale(double ratio, Point center);

	public abstract List<Point> getPoints();

	public abstract Point getStartPoint();

	public abstract Point getEndPoint();

	public abstract SVGCommand getReverseCommand();
	
	public abstract void flipHoriz(double width);

	public SVGCommand getNextCommand() {
		return nextCommand;
	}

	protected void setNextCommand(SVGCommand nextCommand) {
		this.nextCommand = nextCommand;
	}

	public SVGCommand getPrevCommand() {
		return prevCommand;
	}

	protected void setPrevCommand(SVGCommand prevCommand) {
		this.prevCommand = prevCommand;
	}

	public CommandType getType() {
		return type;
	}

	public List<Double> getNumbers() {
		return numbers;
	}

	@Override
	public String toString() {
		return type.name();
	}

}
