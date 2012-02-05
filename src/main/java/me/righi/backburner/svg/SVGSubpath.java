package me.righi.backburner.svg;

import java.util.ArrayList;
import java.util.List;

public class SVGSubpath {

	private List<SVGCommand> commands = new ArrayList<SVGCommand>();

	protected SVGSubpath(List<SVGCommand> commands) {
		this.commands = commands;

		initNeighbors(commands);
	}
	
	private void initNeighbors(List<SVGCommand> commands) {
		for (int i = 0; i < commands.size(); i++) {
			SVGCommand command = commands.get(i);
			SVGCommand next = (i < commands.size() - 1) ? commands.get(i + 1) : commands.get(0);
			SVGCommand prev = (i > 0) ? commands.get(i - 1) : commands.get(commands.size() - 1);
			command.setPrevCommand(prev);
			command.setNextCommand(next);
		}
	}

	public List<SVGCommand> getCommands() {
		return commands;
	}

	public void shift(double x, double y) {
		for (SVGCommand command : commands) {
			command.shift(x, y);
		}
	}

	public void scale(double ratio, Point center) {
		for (SVGCommand command : commands) {
			command.scale(ratio, center);
		}
	}

	
	public boolean isClosed() {
		return this.getLastCommand().type == CommandType.Z;
	}
	
	public void flipHoriz(double width) {
		for (int i = 0; i < commands.size(); i++) {
			SVGCommand command = commands.get(i);
			command.flipHoriz(width);
		}
	}
	
	public void reverse() {
		
		if (isClosed()) {
			SVGSubpath clone = this.clone();
			clone.open();
			clone.reverse();
			clone.close();
			this.commands = clone.commands;
		} else {
			List<SVGCommand> reversedCommands = new ArrayList<SVGCommand>();

			// Add the first one
			reversedCommands.add(getFirstCommand().getReverseCommand());

			for (int i = commands.size() - 1; i > 0; i--) {
				reversedCommands.add(commands.get(i).getReverseCommand());
			}
			initNeighbors(reversedCommands);
			
			this.commands = reversedCommands;
		}
	}

	public SVGCommand getFirstCommand() {
		return commands.get(0);
	}

	public SVGCommand getLastCommand() {
		return commands.get(commands.size() - 1);
	}

	public Point getStartPoint() {
		return getFirstCommand().getStartPoint();
	}

	public Point getEndPoint() {
		return getLastCommand().getEndPoint();
	}

	public void open() {
		synchronized (commands) {
			if (this.isClosed()) {
				commands.remove(commands.size() - 1);
				initNeighbors(commands);
			}
		}
	}

	public void close() {
		synchronized (commands) {
			if (!this.isClosed()) {
				commands.add(SVGCommand.parse("Z"));
				initNeighbors(commands);
			}
		}
	}

	public SVGSubpath clone() {
		List<SVGCommand> clonedCommands = new ArrayList<SVGCommand>();
		for (SVGCommand command : commands) {
			clonedCommands.add(SVGCommand.parse(command.toString()));
		}
		return new SVGSubpath(new ArrayList<SVGCommand>(clonedCommands));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (SVGCommand command : commands) {
			sb.append(command.toString() + " ");
		}
		return sb.toString().trim();
	}

}
