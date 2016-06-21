package pt.uab.hm;

import pt.uab.hm.ui.ORTRVisualizer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Bruno Vitorino <bruno.vitorino@gmail.com>
 *         <p>
 *         This is the class responsible for applying the ORTR algorithm for the
 *         OVRP problem.
 */
public class Solver {

    public Solver() {

    }

    public static void main(String[] args) throws Throwable {
        List<Customer> customers = OVRPGenerator.generate(20, 10);

        for (Customer customer : customers) {
            System.out.println(customer);
        }

        Solver solver = new Solver();
        solver.solve(customers, 900);
    }

    /**
     * Compare two customers by using their angles.
     *
     * @param a
     * @param b
     * @return -1, 0, 1
     */
    public static int compareCustomersByAngle(Customer a, Customer b) {
        if (a.getPointPolar().getTheta() > b.getPointPolar().getTheta()) {
            return 1;
        } else if (a.getPointPolar().getTheta() < b.getPointPolar().getTheta()) {
            return -1;
        }
        return 0;
    }

    public void solve(List<Customer> customers, int vehicleCapacity) throws Throwable {
        int I = 2;
        int K = 5;
        double M = Math.max(customers.size() / 2, 30);
        int NB_LIST_SIZE = 20;
        double deviation;

        // Apply sweep algorithm
        Solution solution = this.sweep(customers, vehicleCapacity);

        ORTRVisualizer.visualize(solution.getVehicles());

        double record = solution.runObjectiveFunction(), bestRecord = record;

        // Calculate deviation
        deviation = .01 * record;

        int itr = 0;

        while (itr <= M) {
            int count = 0;

            while (count <= K) {

                for (int i = 1; i < I; i++) {

                    // One Point Move with record-to-record travel
                    Solution newSolution = this.onePointMoveRTR(solution, vehicleCapacity);

                    // Two Point Move with record-to-record travel between
                    // routes
                    newSolution = this.twoPointMoveRTR(newSolution, vehicleCapacity);
                    // Two-opt Move with record-to-record travel
                    // Feasibility must be maintained

                    if (!newSolution.getCustomers().equals(solution.getCustomers())
                            && !(newSolution.getSolutionValue() == solution.getSolutionValue())) {

                        record = newSolution.getSolutionValue();
                        count = 0;
                        solution = newSolution;
                    }

                }

                ++count;
            }

            if (record < bestRecord) {
                bestRecord = record;
                itr = 0;
            } else {
                ++itr;
            }
        }

        ORTRVisualizer.visualize(solution.getVehicles());
    }

    /**
     * @param customers       List of customers to be served.
     * @param vehicleCapacity Capacity in terms of customer demand.
     * @return A list of vehicles with customers associated to each of the
     * vehicles.
     * @throws Exception
     */
    public Solution sweep(List<Customer> customers, Integer vehicleCapacity) throws Exception {

        List<Customer> customersCopy = new ArrayList<Customer>(customers);

        if (vehicleCapacity <= 0) {
            throw new Exception("vehicleCapacity must be > 0");
        }

        // Sort array by angle
        customersCopy.sort(Solver::compareCustomersByAngle);

        // Initialize the list of vehicles
        List<Vehicle> vehicles = new ArrayList<>();

        Iterator<Customer> it = customersCopy.iterator();

        // Create the depot - initial point of any vehicle
        Customer depot = new Customer(it.next().getPoint(), 0);

        int vehicleNumber = 0;

        Customer customer = it.next();
        Vehicle vehicle = new Vehicle(vehicleNumber);
        vehicle.addCustomer(depot);

        do {
            // Check vehicle capacity
            if (vehicle.getCurrentCapacityUsage() + customer.getDemand() <= vehicleCapacity) {
                // Add customer if the vehicle still has 'space'
                vehicle.addCustomer(customer);
                customer.setVehicle(vehicle);
            } else {
                // Vehicle can't take anymore customers
                vehicles.add(vehicle);
                vehicle = new Vehicle(++vehicleNumber);
                vehicle.addCustomer(depot);
                vehicle.addCustomer(customer);
            }
        } while (it.hasNext() && (customer = it.next()) != null);
        // Add the last created vehicle
        vehicles.add(vehicle);

        return new Solution(vehicles, customers, vehicleCapacity);
    }

    /**
     * @param solution
     * @param vehicleCapacity
     * @return
     */
    public Solution onePointMoveRTR(Solution solution, int vehicleCapacity) {
        Solution bestSolution = null;
        double bestValueSoFar;
        int elementToChange = -1;
        int bestPosition = -1;

        double currentValue = solution.runObjectiveFunction();

        bestValueSoFar = currentValue;

        // At the position = 0 is the depot, not to be considered
        for (int i = 1; i < solution.getCustomers().size() - 1; i++) {

            // The current customer to be placed in between the edges
            Point currentPoint = solution.getCustomers().get(i).getPoint();

            Point previousPoint = solution.getCustomers().get(i - 1).getPoint();

            // Calculate solution value without the current customer
            double iterationValue = currentValue;

            if (i == solution.getCustomers().size() - 1) {
                iterationValue -= previousPoint.distanceTo(currentPoint);
            } else {

                Point nextPoint = solution.getCustomers().get(i + 1).getPoint();

                iterationValue -= previousPoint.distanceTo(currentPoint);
                iterationValue -= currentPoint.distanceTo(nextPoint);
                iterationValue += previousPoint.distanceTo(nextPoint);
            }


            for (int j = 0; j < solution.getCustomers().size() - 1; j++) {

                // Don't consider the edges that contain the current element
                // being replaced
                if (j == i - 1 || j == i) {
                    continue;
                }

                double subIterationValue = iterationValue;

                // Edge
                Point edgeStart = solution.getCustomers().get(j).getPoint();
                Point edgeEnd = solution.getCustomers().get(j + 1).getPoint();

                // Calculations for new value
                subIterationValue -= edgeStart.distanceTo(edgeEnd);

                subIterationValue += edgeStart.distanceTo(currentPoint);
                subIterationValue += currentPoint.distanceTo(edgeEnd);

                // If the new value is better than the current one
                if (subIterationValue < bestValueSoFar) {
                    bestPosition = j;
                    elementToChange = i;
                    bestValueSoFar = subIterationValue;
                }

            }

        }

        if (elementToChange != -1 && bestPosition != -1) {
            List<Customer> customers = new ArrayList<>(solution.getCustomers());
            Customer c = customers.remove(elementToChange);
            customers.add(bestPosition, c);

            bestSolution = new Solution(customers, bestValueSoFar, vehicleCapacity);
        }

        return bestSolution != null ? bestSolution : solution;
    }

    /**
     * @param solution
     * @param vehicleCapacity
     * @return
     */
    public Solution twoPointMoveRTR(Solution solution, int vehicleCapacity) {
        double currentValue = solution.runObjectiveFunction(), bestValueSoFar = currentValue;
        int positionToReplaceI = -1, positionToReplaceJ = -1;

        for (int i = 0; i < solution.getCustomers().size(); i++) {

            double iterationValue = currentValue;

            Customer currentCustomerI = solution.getCustomers().get(i);
            Customer previousCustomerI = null, nextCustomerI = null;

            // Remove element I

            if (i > 0) {
                previousCustomerI = solution.getCustomers().get(i - 1);

                iterationValue -= previousCustomerI.getPoint().distanceTo(currentCustomerI.getPoint());
            }

            if (i < solution.getCustomers().size() - 1) {
                nextCustomerI = solution.getCustomers().get(i + 1);

                iterationValue -= nextCustomerI.getPoint().distanceTo(currentCustomerI.getPoint());
            }

            if (previousCustomerI != null && nextCustomerI != null) {
                iterationValue += previousCustomerI.getPoint().distanceTo(nextCustomerI.getPoint());
            }


            // Let's start replacing I with J
            for (int j = 0; j < solution.getCustomers().size(); j++) {
                // Skip if we are trying the element with itself
                if (i == j) {
                    continue;
                }

                // Remove element J

                Customer currentCustomerJ = solution.getCustomers().get(i);
                Customer previousCustomerJ = null, nextCustomerJ = null;

                if (j > 0) {
                    previousCustomerJ = solution.getCustomers().get(j - 1);

                    iterationValue -= previousCustomerJ.getPoint().distanceTo(currentCustomerJ.getPoint());
                }

                if (j < solution.getCustomers().size() - 1) {
                    nextCustomerJ = solution.getCustomers().get(j + 1);

                    iterationValue -= nextCustomerJ.getPoint().distanceTo(currentCustomerJ.getPoint());
                }

                if (previousCustomerJ != null && nextCustomerJ != null) {
                    iterationValue += previousCustomerJ.getPoint().distanceTo(nextCustomerJ.getPoint());
                }

                // Place element I in J position

                if (previousCustomerJ != null) {
                    iterationValue += previousCustomerJ.getPoint().distanceTo(currentCustomerI.getPoint());
                }

                if (nextCustomerJ != null) {
                    iterationValue += nextCustomerJ.getPoint().distanceTo(currentCustomerI.getPoint());
                }

                // Place element J in I position

                if (previousCustomerI != null) {
                    iterationValue += previousCustomerI.getPoint().distanceTo(currentCustomerJ.getPoint());
                }

                if (nextCustomerI != null) {
                    iterationValue += nextCustomerI.getPoint().distanceTo(currentCustomerJ.getPoint());
                }

                // Now let's check if there's some improvement

                if (iterationValue < bestValueSoFar) {
                    bestValueSoFar = iterationValue;
                    positionToReplaceI = i;
                    positionToReplaceJ = j;
                }

            }

        }

        if (positionToReplaceI != -1 && positionToReplaceJ != -1) {

            List<Customer> customers = new ArrayList<>(solution.getCustomers());
            Customer customerI = customers.get(positionToReplaceI);
            Customer customerJ = customers.get(positionToReplaceJ);
            customers.add(positionToReplaceI, customerJ);
            customers.add(positionToReplaceJ, customerI);

            return new Solution(customers, bestValueSoFar, vehicleCapacity);
        } else {
            return solution;
        }
    }
}
