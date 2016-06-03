package pt.uab.hm.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import pt.uab.hm.Customer;
import pt.uab.hm.OVRPGenerator;

public class OVRPGeneratorTest {

	@Test
	public void testGenerate() {
		Customer[] customers = OVRPGenerator.generate(20, 10);
		assertEquals(200, customers.length);
	}

}
