package pt.uab.hm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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
	private List<Customer> customers;

	public static void main(String[] args) throws Throwable {
		List<Customer> customers = OVRPGenerator.generate(10, 20);
		
		Solver solver = new Solver();
		solver.solve(customers);
	}

	public Solver() {

	}

	public void solve(List<Customer> customers) throws Throwable {
		if (customers != null) {
			this.customers = customers;
		}

		this.sweep(this.customers, null);

		int record = this.runObjectiveFunction(), bestRecord = record;

	}

	/**
	 * 
	 * @param customers The customers to be distributed per vehicle
	 * @param vehicleCapacity How many customers per vehicle
	 * @return 
	 * @throws Exception 
	 */
	public List<Vehicle> sweep(List<Customer> customers, Integer vehicleCapacity) throws Exception {
		// Assuming the depot is at the first position
		List<Customer> customersCopy = customers;
		
		if (vehicleCapacity <= 0) {
			throw new Exception("vehicleCapacity must be > 0");
		}
		
		// Sort array by angle
		customersCopy.sort(Solver::compareCustomersByAngle);
		
		List<Vehicle> vehicles = new ArrayList<>();
		Iterator<Customer> it = customersCopy.iterator();
		
		int vehicleNumber = 0;
		while(it.hasNext()) {
			Vehicle vehicle = new Vehicle(++vehicleNumber);
			
			while (vehicle.getCurrentCapacityUsage() <= vehicleCapacity && it.hasNext()) {
				vehicle.addCustomer(it.next());
			}
			
			vehicles.add(vehicle);
		}
		
		// Return customers ordered by angle (theta)
		return vehicles;
	}

	private int runObjectiveFunction() {
		return 0;
	}

	public static int compareCustomersByAngle(Customer a, Customer b) {
		if (a.getPointPolar().getTheta() > b.getPointPolar().getTheta()) {
			return 1;
		} else if (a.getPointPolar().getTheta() < b.getPointPolar().getTheta()) {
			return -1;
		}
		return 0;
	}
}
