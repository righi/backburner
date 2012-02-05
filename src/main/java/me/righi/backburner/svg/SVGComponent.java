package me.righi.backburner.svg;


import java.util.HashMap;
import java.util.Map;

public abstract class SVGComponent {

	protected String id;

	protected Map<String, String> style = new HashMap<String, String>();

	public SVGComponent() {
		style.put("fill", "none");
		style.put("stroke-width", "0.1");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFill() {
		return style.get("fill");
	}

	public void setFill(String fill) {
		style.put("fill", fill);
	}

	public abstract String getXml();

	public abstract void shift(double deltaX, double deltaY);

	public abstract void scale(double ratio, Point center);

	public abstract Rectangle getBounds();

	public String getStyle() {
		StringBuilder sb = new StringBuilder();
		sb.append("style='");

		for (String key : style.keySet()) {
			String value = style.get(key);
			sb.append(key + ":" + value + ";");
		}

		sb.append("'");
		return sb.toString();
	}

}
