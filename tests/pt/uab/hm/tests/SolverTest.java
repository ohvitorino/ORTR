package pt.uab.hm.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import pt.uab.hm.Customer;
import pt.uab.hm.Point;
import pt.uab.hm.Solver;

public class SolverTest {

	@Test
	public void testCompareCustomers() {
		Customer a = new Customer(new Point(3, 4), 40);
		Customer b = new Customer(new Point(1, 4), 40);
		
		int value = Solver.compareCustomers(a, b);
		
		assertEquals(1, value);
	}

}
