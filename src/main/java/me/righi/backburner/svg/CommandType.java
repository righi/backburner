package me.righi.backburner.svg;

public enum CommandType {

	M(AbsoluteMoveToCommand.class, false), Z(ZCommand.class, true), C(AbsoluteCurveToCommand.class, true);

	/*
	 * Not supported:
	 * m relative moveto
	 * L absolute lineto
	 * l relative lineto
	 * H absolute horizontal lineto
	 * h relative horizontal lineto
	 * V absolute vertical lineto
	 * v relative vertical lineto
	 * c relative curveto
	 * S absolute smooth curveto
	 * s relative smooth curveto
	 * Q absolute quadratic Bézier curveto
	 * q relative quadratic Bézier curveto
	 * T absolute smooth quadratic Bézier curveto
	 * t relative smooth quadratic Bézier curveto
	 * A absolute elliptical arc
	 * a relative elliptical arc
	 */

	private Class<? extends SVGCommand> clazz;
	
	private boolean penDown;

	private CommandType(Class<? extends SVGCommand> clazz, boolean penDown) {
		this.clazz = clazz;
		this.penDown = penDown;
	}

	public Class<? extends SVGCommand> getClazz() {
		return clazz;
	}
	
	public boolean isPenDown() {
		return penDown;
	}

	public static CommandType parse(String s) {
		s = s.trim();
		if (s.startsWith("z")) {
			return Z;
		} else {
			for (CommandType t : CommandType.values()) {
				if (s.startsWith(t.name())) {
					return t;
				}
			}
		}

		throw new IllegalArgumentException("Unrecognized svg command: " + s);
	}
}
