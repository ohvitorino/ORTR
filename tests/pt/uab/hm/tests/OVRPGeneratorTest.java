package pt.uab.hm.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import pt.uab.hm.Customer;
import pt.uab.hm.OVRPGenerator;

public class OVRPGeneratorTest {

	@Test
	public void testGenerate() {
		List<Customer> customers = OVRPGenerator.generate(20, 10);
		// This also includes the depot
		assertEquals(201, customers.size());
	}

}
