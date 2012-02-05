package me.righi.backburner.svg;

import java.util.ArrayList;
import java.util.List;

public class ZCommand extends SVGCommand {

	protected ZCommand(CommandType type, String path) {
		super(type, path);
	}

	@Override
	public List<Point> getPoints() {
		return new ArrayList<Point>();
	}

	@Override
	public void shift(double deltaX, double deltaY) {
		// no-op
	}
	
	@Override
	public void scale(double ratio, Point center) {
		// no-op
	}

	@Override
	public void flipHoriz(double width) {
		// no-op
	}
	
	@Override
	public Point getEndPoint() {
		return nextCommand.getStartPoint();
	}

	@Override
	public Point getStartPoint() {
		return this.prevCommand.getEndPoint();
	}
	
	@Override
	public SVGCommand getReverseCommand() {
		return this;
	}

}
