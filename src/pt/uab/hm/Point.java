package pt.uab.hm;

public class Point implements Comparable<Point> {
	private double x;
	private double y;

	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public int compareTo(Point o) {
		if (this.x > o.x || this.y > o.y)
			return 1;
		else if (this.x < o.x || this.y < o.y)
			return -1;
		return 0;
	}
}
