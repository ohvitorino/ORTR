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

	public static void main(String[] args) throws Throwable {
		List<Customer> customers = OVRPGenerator.generate(10, 20);

		Solver solver = new Solver();
		solver.solve(customers);
	}

	public Solver() {

	}

	public void solve(List<Customer> customers) throws Throwable {
		int I = 2;
		int K = 5;
		double M = Math.max(customers.size() / 2, 30);
		int NB_LIST_SIZE = 20;
		double deviation;

		// Apply sweep algorithm
		List<Vehicle> vehicles = this.sweep(customers, null);

		double record = this.runObjectiveFunction(vehicles), bestRecord = record;

		// Calculate deviation
		deviation = .01 * record;

		int itr = 0;

		while (itr <= M) {
			int count = 0;

			while (count <= K) {

				for (int i = 1; i < I; i++) {

					// One Point Move with record-to-record travel
					// Two Point Move with record-to-record travel between
					// routes
					// Two-opt Move with record-to-record travel
					// Feasibility must be maintained

				}
			}
		}
	}

	/**
	 * 
	 * @param customers
	 *            List of customers to be served.
	 * @param vehicleCapacity
	 *            Capacity in terms of customer demand.
	 * @return A list of vehicles with customers associated to each of the
	 *         vehicles.
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
		
		Customer customer = it.next();
		Vehicle vehicle = new Vehicle(vehicleNumber);
		do {
			// Check vehicle capacity
			if (vehicle.getCurrentCapacityUsage() + customer.getDemand() <= vehicleCapacity) {
				// Add customer if the vehicle still has 'space'
				vehicle.addCustomer(customer);
			} else {
				// Vehicle can't take anymore customers
				vehicles.add(vehicle);
				vehicle = new Vehicle(++vehicleNumber);
			}
		} while (it.hasNext() && (customer = it.next()) != null);
		// Add the last created vehicle
		vehicles.add(vehicle);

		// Return customers ordered by angle (theta)
		return vehicles;
	}

	/**
	 * Objective function
	 * 
	 * @param vehicles
	 * @return
	 */
	private double runObjectiveFunction(List<Vehicle> vehicles) {
		double distance = 0;
		for (Vehicle vehicle : vehicles) {
			for (int i = 0; i < vehicle.getCustomers().size(); i++) {
				if (i != vehicle.getCustomers().size() - 1) {
					Customer c1 = vehicle.getCustomers().get(i);
					Customer c2 = vehicle.getCustomers().get(i + 1);
					distance += Math.sqrt((c1.getPoint().getX() - c2.getPoint().getX())
							* (c1.getPoint().getX() - c2.getPoint().getX())
							+ (c1.getPoint().getY() - c2.getPoint().getY())
									* (c1.getPoint().getY() - c2.getPoint().getY()));
				}
			}
		}
		return vehicles.size() * distance;
	}

	/**
	 * Compare two customers by using their angles.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int compareCustomersByAngle(Customer a, Customer b) {
		if (a.getPointPolar().getTheta() > b.getPointPolar().getTheta()) {
			return 1;
		} else if (a.getPointPolar().getTheta() < b.getPointPolar().getTheta()) {
			return -1;
		}
		return 0;
	}
	
	private void onePointMoveRTR(List<Customer> customers) {
		
	}
	
	private void twoPointMoveRTR(List<Customer> customers) {
		
	}
}
