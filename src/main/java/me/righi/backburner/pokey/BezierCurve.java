package me.righi.backburner.pokey;

public class BezierCurve {

	private Point anchor1;
	private Point control1;
	private Point control2;
	private Point anchor2;
	
	
	
	public BezierCurve(Point anchor1, Point control1, Point control2, Point anchor2) {
		this.anchor1 = anchor1;
		this.control1 = control1;
		this.control2 = control2;
		this.anchor2 = anchor2;
	}

	public static BezierCurve create(Point anchor1, Point control1, Point control2, Point anchor2) {
		return new BezierCurve(anchor1, control1, control2, anchor2);
	}

	public Point getAnchor1() {
		return anchor1;
	}

	// A convenience to make it easier to conver the JavaScript code to Java.
	public String svg() {
		return this.getSvg();
	}
	
	public String getSvg() {
		return BezierCurve.buildSvg(anchor1, control1, control2, anchor2);
	}

	public static String buildSvg(Point anchor1, Point control1, Point control2, Point anchor2) {
		StringBuilder svg = new StringBuilder("M" + anchor1 + " ");
		svg.append("C" + control1 + " ");
		svg.append(control2 + " ");
		svg.append(anchor2 + " ");
		return svg.toString();
	}

	public void setAnchor1(Point anchor1) {
		this.anchor1 = anchor1;
	}

	public Point getControl1() {
		return control1;
	}

	public void setControl1(Point control1) {
		this.control1 = control1;
	}

	public Point getControl2() {
		return control2;
	}

	public void setControl2(Point control2) {
		this.control2 = control2;
	}

	public Point getAnchor2() {
		return anchor2;
	}

	public void setAnchor2(Point anchor2) {
		this.anchor2 = anchor2;
	}
	
	public BezierCurve[] split(double t) {
		// Merci, Paul de Casteljau!
		Point midPt1 = Point.create(this.anchor1.x + t * (this.control1.x - this.anchor1.x), this.anchor1.y + t * (this.control1.y - this.anchor1.y));
	    Point midPt2 = Point.create(this.control1.x + t * (this.control2.x - this.control1.x), this.control1.y + t * (this.control2.y - this.control1.y));
		Point midPt3 = Point.create(this.control2.x + t * (this.anchor2.x - this.control2.x), this.control2.y + t * (this.anchor2.y - this.control2.y));
		Point midPt4 = Point.create(midPt1.x + t * (midPt2.x - midPt1.x), midPt1.y + t * (midPt2.y - midPt1.y));
		Point midPt5 = Point.create(midPt2.x + t * (midPt3.x - midPt2.x), midPt2.y + t * (midPt3.y - midPt2.y));
		Point sharedAnchor = Point.create(midPt4.x + t * (midPt5.x - midPt4.x), midPt4.y + t * (midPt5.y - midPt4.y));
		BezierCurve[] result = {
						BezierCurve.create(this.anchor1.clone(), midPt1, midPt4, sharedAnchor), //
						BezierCurve.create(sharedAnchor.clone(), midPt5, midPt3, this.anchor2.clone()) //
						};
		return result;
	}

}
