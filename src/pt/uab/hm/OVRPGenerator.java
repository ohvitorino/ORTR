package pt.uab.hm;

import java.util.ArrayList;
import java.util.Arrays;
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

		// Algorithm
		int w = 0;

		// This is the depot
		customers.add(new Customer(new Point(0, 0), 0));

		for (int k = 1; k <= b; k++) {
			int gama = 30 * 1000;

			for (int i = 1; i <= a; i++) {
				w++;

				double x = gama * Math.cos(2 * (i - 1) * Math.PI / a);
				double y = gama * Math.sin(2 * (i - 1) * Math.PI / a);

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
