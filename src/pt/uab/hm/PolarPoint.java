package pt.uab.hm;

public class PolarPoint {
	private double theta;
	private double r;

	public PolarPoint() {
	}

	public PolarPoint(double theta, double r) {
		this.theta = theta;
		this.r = r;
	}

	public double getTheta() {
		return theta;
	}

	public double getR() {
		return r;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	public void setR(double r) {
		this.r = r;
	}
}
