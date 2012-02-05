package me.righi.backburner.svg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SVGPath extends SVGComponent {

	private List<SVGSubpath> subpaths = new ArrayList<SVGSubpath>();

	public SVGPath(String svg) {
		this(null, svg);
	}

	public SVGPath(String id, String svg) {
		this.id = id;
		List<String> strings = SVGUtil.split(svg);
		List<SVGCommand> commands = new ArrayList<SVGCommand>();
		boolean firstRun = true;
		for (String s : strings) {
			SVGCommand command = SVGCommand.parse(s);
			if (!firstRun && !command.type.isPenDown()) {
				subpaths.add(new SVGSubpath(commands));
				commands = new ArrayList<SVGCommand>();
			}
			commands.add(command);
			firstRun = false;
		}
		subpaths.add(new SVGSubpath(commands));
	}

	public void connectSubpaths() {
		List<SVGCommand> newCommands = new ArrayList<SVGCommand>();
		for (int a = 0; a < subpaths.size(); a++) {
			SVGSubpath path = subpaths.get(a);
			if (a < subpaths.size() - 1) {
				path.open();
			}

			List<SVGCommand> commands = path.getCommands();
			for (int b = 0; b < commands.size(); b++) {
				SVGCommand command = commands.get(b);
				if (a == 0 || command.getType().isPenDown()) {
					newCommands.add(command);
				}
			}
		}
		subpaths.clear();
		subpaths.add(new SVGSubpath(newCommands));
	}

	public List<SVGSubpath> getSubpaths() {
		return subpaths;
	}

	public void shift(double x, double y) {
		for (SVGSubpath subpath : subpaths) {
			subpath.shift(x, y);
		}
	}

	@Override
	public void scale(double ratio, Point center) {
		for (SVGSubpath subpath : subpaths) {
			subpath.scale(ratio, center);
		}
	}

	public void reverse() {
		for (SVGSubpath subpath : subpaths) {
			subpath.reverse();
		}
		Collections.reverse(subpaths);
	}

	public void flipHoriz(double width) {
		for (SVGSubpath subpath : subpaths) {
			subpath.flipHoriz(width);
		}
	}

	public Point getStartPoint() {
		return subpaths.get(0).getStartPoint();
	}

	public Point endPoint() {
		return subpaths.get(subpaths.size() - 1).getEndPoint();
	}
	
	@Override
	public Rectangle getBounds() {
		java.awt.Shape shape = SVGUtil.makeShape(this.toString());
		java.awt.Rectangle bounds = shape.getBounds();
		return new Rectangle(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
	}

	@Override
	public String getXml() {
		String idStr = (id == null) ? "" : "id='" + id + "' ";
		return "<path " + idStr + "d='" + this.toString() + "' " + this.getStyle() + "/>";
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (SVGSubpath subpath : subpaths) {
			sb.append(subpath.toString() + " ");
		}
		return sb.toString().trim();
	}

}
