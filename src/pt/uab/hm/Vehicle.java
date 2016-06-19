package pt.uab.hm;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Bruno Vitorino <bruno.vitorino@gmail.com>
 *
 */
public class Vehicle {
	private int number;
	private List<Customer> customers;
	
	public Vehicle(int number) {
		this.number = number;
		this.customers = new ArrayList<>();
	}
	
	public void addCustomer(Customer customer) {
		this.customers.add(customer);
	}
	
	public long getCurrentCapacityUsage() {
		long value = 0;
		for (Customer customer : customers) {
			value += customer.getDemand();
		}
		return value;
	}

	public int getNumber() {
		return number;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	/**
	 * Given a list of customers, select the ones that have
	 * the current vehicle assigned to them.
	 * @param customers
	 * @return List of customers assign to this vehicle
	 */
	public List<Customer> getCustomers(List<Customer> customers) {
		List<Customer> myCustomers = new ArrayList<Customer>();
		for (Customer customer : customers) {
			if (customer.getVehicle() == this) {
				myCustomers.add(customer);
			}
		}
		return myCustomers;
	}
}
