package me.righi.backburner.svg;

import java.util.ArrayList;
import java.util.List;

public class SVGOutput {

	public static final double PIXELS_PER_INCH = 90;
	public static final double PIXELS_PER_MM = 3.543;
	
	private List<SVGComponent> components = new ArrayList<SVGComponent>();

	private Double width;

	private Double height;

	private Rectangle viewBox;

	public SVGOutput() {

	}

	public void addComponent(SVGComponent svg) {
		components.add(svg);
	}

	public List<SVGComponent> getComponents() {
		return components;
	}

	public void setComponents(List<SVGComponent> components) {
		this.components = components;
	}

	public Rectangle getViewBox() {
		return viewBox;
	}

	public void setViewBox(Rectangle viewBox) {
		this.viewBox = viewBox;
	}

	public void setDimensions(Double width, Double height) {
		this.setWidth(width);
		this.setHeight(height);
	}
	
	public Double getWidth() {
		return width;
	}

	public void setWidth(Double width) {
		this.width = width;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public void shift(double deltaX, double deltaY) {
		for (SVGComponent comp : components) {
			comp.shift(deltaX, deltaY);
		}
	}

	public void scale(double ratio, Point center) {
		for (SVGComponent comp : components) {
			comp.scale(ratio, center);
		}
	}

	public void crop() {
		this.crop(true, true);
	}
	
	// TODO For some reason the width is much larger than it should be.  I think the problem might be that SVGPath uses AWT to calculate the bounds, which are calculating pixels instead of mm???
	public void crop(boolean cropX, boolean cropY) {
		double minX = Double.MAX_VALUE;
		double minY = Double.MAX_VALUE;
		double maxX = Double.MIN_VALUE;
		double maxY = Double.MIN_VALUE;

		for (SVGComponent component : components) {
			Rectangle bounds = component.getBounds();
			minX = (bounds.getMinX() < minX) ? bounds.getMinX() : minX;
			minY = (bounds.getMinY() < minY) ? bounds.getMinY() : minY;
			maxX = (bounds.getMaxX() > maxX) ? bounds.getMaxX() : maxX;
			maxY = (bounds.getMaxY() > maxY) ? bounds.getMaxY() : maxY;
		}

		double deltaX = 0;
		double deltaY = 0;
		
		if (cropX) {
			deltaX = -minX;
			setWidth(maxX - minX);
		}
		
		if (cropY) {
			deltaY = -minY;
			setHeight(maxY - minY);
		}
		
		this.shift(deltaX, deltaY);
	}

	public String getXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE svg PUBLIC '-//W3C//DTD SVG 1.1//EN' 'http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd'>\n");
		String widthStr = "", heightStr = "", viewBoxStr = "";
		if (width != null && height != null) {
			widthStr = "width='" + width + "mm'";
			heightStr = "height='" + height + "mm'";
		}

		if (viewBox != null) {
			viewBoxStr = "viewBox='" + viewBox.getMinX() + " " + viewBox.getMinY() + " " + viewBox.getWidth() + " " + viewBox.getHeight() + "'";
		}

		String style = "style='stroke:black;'";

		sb.append("<svg xmlns='http://www.w3.org/2000/svg' version='1.1' " + widthStr + " " + heightStr + " " + viewBoxStr + " " + style + ">");
		for (SVGComponent comp : components) {
			sb.append(comp.getXml());
		}
		sb.append("</svg>");

		return sb.toString();
	}

	@Override
	public String toString() {
		return this.getXml();
	}

}
