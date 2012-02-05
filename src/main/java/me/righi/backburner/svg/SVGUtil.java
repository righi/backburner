package me.righi.backburner.svg;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.batik.ext.awt.geom.ExtendedGeneralPath;
import org.apache.batik.parser.AWTPathProducer;

public class SVGUtil {

	private static final Pattern NUMBER_REGEX = Pattern.compile("-?\\d+(\\.\\d*)?");
	
	private static final Pattern SVG_FULL_PATH_REGEX = Pattern.compile("[a-z][^a-z]*", Pattern.CASE_INSENSITIVE);
	private static final Pattern SVG_POINT_REGEX = Pattern.compile("(-?\\d+)(.\\d+)?\\s*,\\s*(-?\\d+)(.\\d+)?");
	private static final Pattern SVG_CUBIC_CURVE = Pattern.compile("(C)(\\s*-?\\d+.?\\d*\\s*),(\\s*-?\\d+.?\\d*\\s*)(\\s*-?\\d+.?\\d*\\s*),(\\s*-?\\d+.?\\d*\\s*)(\\s*-?\\d+.?\\d*\\s*),(\\s*-?\\d+.?\\d*\\s*)", Pattern.CASE_INSENSITIVE);

	private static List<String> split(String s, Pattern pattern) {
		List<String> tokens = new ArrayList<String>();
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			String token = matcher.group();
			tokens.add(token);
		}
		return tokens;

	}

	private static List<String> groups(String s, Pattern pattern) {
		List<String> tokens = new ArrayList<String>();
		Matcher matcher = pattern.matcher(s);
		while (matcher.find()) {
			int groupCount = matcher.groupCount();
			for (int i = 0; i <= groupCount; i++) {
				String group = matcher.group(i);
				if (group != null) {
					group = group.trim();
					tokens.add(group);
				}
			}
		}
		return tokens;
	}

	public static List<String> split(String svgPath) {
		return split(svgPath, SVG_FULL_PATH_REGEX);
	}

	public static List<Point> points(String svgPath) {
		List<Point> points = new ArrayList<Point>();
		List<String> groups = groups(svgPath, SVG_POINT_REGEX);
		for (String group : groups) {
			if (group.matches(SVG_POINT_REGEX.pattern())) {
				String[] tokens = group.split(",");
				points.add(new Point(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1])));
			}
		}
		return points;
	}
	
	public static List<Double> numbers(String svgPath) {
		List<Double> nums = new ArrayList<Double>();
		List<String> groups = groups(svgPath, NUMBER_REGEX);
		for (String group : groups) {
			if (group.matches(NUMBER_REGEX.pattern())) {
				nums.add(new Double(group));
			}
		}
		return nums;
	}

	public static Point firstPoint(String svgPath) {
		String first = groups(svgPath, SVG_POINT_REGEX).get(1);
		String[] tokens = first.split(",");
		return new Point(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]));
	}

	public static Point lastPoint(String svgPath) {
		List<String> groups = groups(svgPath, SVG_POINT_REGEX);
		String last = groups.get(groups.size() - 1);
		String[] tokens = last.split(",");
		return new Point(Double.parseDouble(tokens[0]), Double.parseDouble(tokens[1]));
	}

	public static String reverse(String svgPath) {
		StringBuilder sb = new StringBuilder();

		List<String> tokens = split(svgPath);
		Collections.reverse(tokens);

		String zToken = "";
		String mToken = "";

		for (int i = 0; i < tokens.size(); i++) {
			String token = tokens.get(i);
			String iToken = token.toUpperCase();

			if (iToken.startsWith("Z")) {
				zToken = token;
			} else if (i == tokens.size() - 1 && iToken.startsWith("M")) {
				mToken = token;
			} else {
				if (iToken.startsWith("C")) {
					List<String> cTokens = groups(iToken, SVG_CUBIC_CURVE);
					token = "C " + cTokens.get(6) + "," + cTokens.get(7) + " " + cTokens.get(4) + "," + cTokens.get(5) + cTokens.get(2) + "," + cTokens.get(3);
				}

				sb.append(token + " ");
			}
		}
		sb.insert(0, mToken);
		sb.append(zToken);

		return sb.toString().trim();
	}

	public static Shape shift(Shape shape, double deltaX, double deltaY) {
		AffineTransform transform = new AffineTransform();
		transform.translate(deltaX, deltaY);
		return transform.createTransformedShape(shape);
	}

	public static Shape flipHoriz(Shape shape) {
		AffineTransform transform = new AffineTransform();
		transform.scale(-1.0, 1.0);
		return transform.createTransformedShape(shape);
	}

	public static Shape makeShape(String pathData) {
		try {
			ExtendedGeneralPath extendedPath = (ExtendedGeneralPath) AWTPathProducer.createShape(new StringReader(pathData), PathIterator.WIND_EVEN_ODD);
			GeneralPath path = new GeneralPath();
			float[] coords = new float[6];
			for (PathIterator itr = extendedPath.getPathIterator(null); !itr.isDone(); itr.next()) {
				int type = itr.currentSegment(coords);
				switch (type) {
					case PathIterator.SEG_CUBICTO:
						path.curveTo(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
						break;
					case PathIterator.SEG_QUADTO:
						path.quadTo(coords[0], coords[1], coords[2], coords[3]);
						break;
					case PathIterator.SEG_LINETO:
						path.lineTo(coords[0], coords[1]);
						break;
					case PathIterator.SEG_MOVETO:
						path.moveTo(coords[0], coords[1]);
						break;
					case PathIterator.SEG_CLOSE:
						path.closePath();
						break;
				}
			}
			return path;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
