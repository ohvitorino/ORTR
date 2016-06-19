package pt.uab.hm.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pt.uab.hm.Customer;
import pt.uab.hm.OVRPGenerator;
import pt.uab.hm.Point;
import pt.uab.hm.Solution;
import pt.uab.hm.Solver;
import pt.uab.hm.Vehicle;
import pt.uab.hm.ui.ORTRVisualizer;

public class SolverTest {

	@Test
	public void testCompareCustomers() {
		Customer a = new Customer(new Point(3, 4), 40);
		Customer b = new Customer(new Point(1, 4), 40);

		int value = Solver.compareCustomersByAngle(a, b);

		assertEquals(1, value);
	}

	@Test
	public void testSweepO1() {
		Solver solver = new Solver();
		Solution solution;
		List<Customer> customers;

		try {
			// Get a problem to solve
			customers = OVRPGenerator.generate(20, 10);
			
			solution = solver.sweep(customers, 900);
			assertEquals(5, solution.getVehicles().size());
			for (Vehicle vehicle : solution.getVehicles()) {
				// Check if the depot is the first "customer"
				assertTrue(vehicle.getCustomers().get(0).getPoint().compareTo(new Point(0, 0)) == 0);
			}
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testSweepO2() {
		Solver solver = new Solver();
		Solution solution;
		List<Customer> customers;

		try {
			// Get a problem to solve
			customers = OVRPGenerator.generate(40, 6);
			solution = solver.sweep(customers, 550);
			assertEquals(9, solution.getVehicles().size());
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void testSweepO3() {
		Solver solver = new Solver();
		Solution solution;
		List<Customer> customers;

		try {
			// Get a problem to solve
			customers = OVRPGenerator.generate(28, 10);
			solution = solver.sweep(customers, 900);
			assertEquals(7, solution.getVehicles().size());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testOnePointMoveRTR() {
		final int CAPACITY = 300;
		
		Solver solver = new Solver();
		
		List<Customer> customers = new ArrayList<>();

		customers.add(new Customer(new Point(0, 0), 10));
		customers.add(new Customer(new Point(4, 4), 10));
		customers.add(new Customer(new Point(3, 3), 10));
		customers.add(new Customer(new Point(2, 2), 10));
		customers.add(new Customer(new Point(1, 1), 10));

		Solution solution = new Solution(null, customers, CAPACITY);
//		solution.distributeByVehicles(CAPACITY);
		
		solver.onePointMoveRTR(solution, CAPACITY);

	}

    @Test
    public void testOnePointMoveRTRWithGeneratedSolution() {
        final int CAPACITY = 900;

        Solver solver = new Solver();

        List<Customer> customers = OVRPGenerator.generate(5, 6);

        Solution solution = new Solution(null, customers, CAPACITY);
		solution.distributeByVehicles(CAPACITY);

        ORTRVisualizer.visualize(solution.getVehicles());

        solution = solver.onePointMoveRTR(solution, CAPACITY);


        ORTRVisualizer.visualize(solution.getVehicles());
    }
}
