package pt.uab.hm;

import java.util.ArrayList;
import java.util.List;

public class OVRPGenerator {
	/**
	 * Generate customers
	 * 
	 * @param n
	 *            number of customers
	 * @return Array of customers
	 */
	public static List<Customer> generate(int a, int b) {
		// Number of customers is determined by a and b
		int n = a * b;

		List<Customer> customers = new ArrayList<>(n + 1);


		// This is the depot
		customers.add(new Customer(new Point(0, 0), 0));

		for (int k = 1; k <= b; k++) {
			int gama = 30 * k;

			for (int i = 1; i <= a; i++) {

				double x = gama * Math.cos(2 * (i - 1) * Math.PI / a);
				double y = gama * Math.sin(2 * (i - 1) * Math.PI / a);

				// Round to 4 decimal places
				x = Math.round (x * 10000.0) / 10000.0;
				y = Math.round (y * 10000.0) / 10000.0;
				
				int demand;
				int mod = Math.floorMod(i, 4);
				if (mod == 2 || mod == 3) {
					demand = 30;
				} else {
					demand = 10;
				}

				customers.add(new Customer(new Point(x, y), demand));
			}
		}

		return customers;
	}
}
