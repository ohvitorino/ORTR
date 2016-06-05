package pt.uab.hm.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import pt.uab.hm.Customer;
import pt.uab.hm.OVRPGenerator;
import pt.uab.hm.Point;
import pt.uab.hm.Solver;
import pt.uab.hm.Vehicle;

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
		// Get a problem to solve
		Solver solver = new Solver();
		List<Vehicle> vehicles;
		List<Customer> customers;
		
		try {
			
			customers = OVRPGenerator.generate(20, 10);
			vehicles = solver.sweep(customers, 900);
			assertEquals(5, vehicles.size());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSweepO2() {
		// Get a problem to solve
		Solver solver = new Solver();
		List<Vehicle> vehicles;
		List<Customer> customers;
		
		try {			
			customers = OVRPGenerator.generate(40, 6);
			vehicles = solver.sweep(customers, 550);
			assertEquals(9, vehicles.size());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void testSweepO3() {
		// Get a problem to solve
		Solver solver = new Solver();
		List<Vehicle> vehicles;
		List<Customer> customers;
		
		try {			
			customers = OVRPGenerator.generate(28, 10);
			vehicles = solver.sweep(customers, 900);
			assertEquals(7, vehicles.size());
		} catch (Exception e) {
			fail();
		}
	}
}
