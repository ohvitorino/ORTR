package pt.uab.hm;

import java.util.ArrayList;
import java.util.List;

public class Solution {
    private List<Vehicle> vehicles;
    private List<Customer> customers;
    private double solutionValue;
    private int capacity;

    public Solution(List<Customer> customers, double solutionValue, int capacity) {
        this(null, customers, capacity);
        this.solutionValue = solutionValue;
    }
    public Solution(List<Vehicle> vehicles, List<Customer> customers, int capacity) {
        this.customers = customers;
        this.capacity = capacity;

        if (vehicles == null) {
            this.distributeByVehicles(capacity);
        } else {
            this.vehicles = vehicles;
        }
    }

    public double getSolutionValue() {
        return solutionValue;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<Vehicle> getVehicles() {
        distributeByVehicles(this.capacity);
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    /**
     * @param vehicleCapacity Vehicle capacity
     */
    public void distributeByVehicles(int vehicleCapacity) {
        this.vehicles = new ArrayList<>();

        int i = 0;

        Vehicle vehicle = new Vehicle(i);
        for (Customer customer : this.customers) {

            if (vehicle.getCurrentCapacityUsage() + customer.getDemand() > vehicleCapacity) {
                this.vehicles.add(vehicle);
                vehicle = new Vehicle(++i);
            }

            vehicle.addCustomer(customer);
            customer.setVehicle(vehicle);
        }
        // Add last created vehicle
        this.vehicles.add(vehicle);
    }

    /**
     * Objective function
     *
     * @return
     */
    double runObjectiveFunction() {
        this.solutionValue = 0;
        for (Vehicle vehicle : this.vehicles) {
            for (int i = 0; i < vehicle.getCustomers().size(); i++) {
                if (i != vehicle.getCustomers().size() - 1) {

                    Customer c1 = vehicle.getCustomers().get(i);
                    Customer c2 = vehicle.getCustomers().get(i + 1);

                    this.solutionValue += c1.getPoint().distanceTo(c2.getPoint());
                }
            }
        }
        return this.vehicles.size() * solutionValue;
    }
}
