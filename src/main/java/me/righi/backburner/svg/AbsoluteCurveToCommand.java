package me.righi.backburner.svg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AbsoluteCurveToCommand extends AbsolutePointCommand {

	protected AbsoluteCurveToCommand(CommandType type, String path) {
		super(type, path);
	}

	protected AbsoluteCurveToCommand(CommandType type, List<Point> points) {
		super(type, points);
	}
	
	@Override
	public Point getStartPoint() {
		return this.prevCommand.getEndPoint();
	}

	@Override
	public SVGCommand getReverseCommand() {
		List<Point> result = new ArrayList<Point>();
		
		List<Point> reversed = new ArrayList<Point>(this.getPoints());
		Collections.reverse(reversed);

		for (int i = 1; i < reversed.size(); i++) {
			result.add(reversed.get(i));
		}
		result.add(this.getStartPoint());
		
		return SVGCommand.parse(type, result);

	}

}
