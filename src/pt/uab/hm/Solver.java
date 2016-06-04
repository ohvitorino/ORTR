package pt.uab.hm;

import java.util.Arrays;

/**
 * 
 * @author Bruno Vitorino <bruno.vitorino@gmail.com>
 *
 */
public class Solver {
	private static final int I = 2;
	private static final int K = 5;
	private static final int NB_LIST_SIZE = 20;

	private int M;
	private Customer[] customers;

	public static void main(String[] args) {
		Customer[] customers = OVRPGenerator.generate(10, 20);
		
		Solver solver = new Solver();
		solver.solve(customers);
	}

	public Solver() {

	}

	public void solve(Customer[] customers) {
		if (customers != null) {
			this.customers = customers;
		}

		this.sweep(this.customers, null, null);

		int record = this.runObjectiveFunction(), bestRecord = record;

	}

	/**
	 * 
	 * @param customers The customers to be distributed per vehicle
	 * @param numberOfVehicles Number of available vehicles
	 * @param vehicleCapacity How many customers per vehicle
	 * @return 
	 */
	private Customer[] sweep(Customer[] customers, Integer numberOfVehicles, Integer vehicleCapacity) {
		// Assuming the depot is at the first position
		Arrays.sort(customers, Solver::compareCustomers);
		
		// Return customers ordered by angle (theta)
		return customers;
	}

	private int runObjectiveFunction() {
		return 0;
	}

	public static int compareCustomers(Customer a, Customer b) {
		if (a.getPointPolar().getTheta() > b.getPointPolar().getTheta()) {
			return 1;
		} else if (a.getPointPolar().getTheta() < b.getPointPolar().getTheta()) {
			return -1;
		}
		return 0;
	}
}
