package me.righi.backburner.svg;

import java.util.Arrays;
import java.util.List;

public class AbsoluteMoveToCommand extends AbsolutePointCommand {

	protected AbsoluteMoveToCommand(CommandType type, String path) {
		super(type, path);
	}

	protected AbsoluteMoveToCommand(CommandType type, List<Point> points) {
		super(type, points);
	}

	@Override
	public SVGCommand getReverseCommand() {
		return SVGCommand.parse(type, Arrays.asList(this.prevCommand.getEndPoint()));
	}

}
