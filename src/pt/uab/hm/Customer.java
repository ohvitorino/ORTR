package pt.uab.hm;

public class Customer {
	private Point point;
	private PolarPoint pointPolar;
	private int demand;
	private Vehicle vehicle;

	public Customer(Point point, int demand) {
		this.point = point;
		this.pointPolar = this.convertCartesianToPolar(point);
		this.demand = demand;
	}

	public Point getPoint() {
		return point;
	}

	public PolarPoint getPointPolar() {
		return pointPolar;
	}

	public int getDemand() {
		return demand;
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public void setPointPolar(PolarPoint pointPolar) {
		this.pointPolar = pointPolar;
	}

	public void setDemand(int demand) {
		this.demand = demand;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	private PolarPoint convertCartesianToPolar(Point point) {
		double theta = Math.atan2(point.getY(), point.getY());
		double r = Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2));
		return new PolarPoint(r, theta);
	}
	
	@Override
	public String toString() {
		return "[" + this.getPoint() + ", " + this.demand + "]";
	}
}
