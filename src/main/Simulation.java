package main;

import config.Configuration;
import config.SystemInitializer;
import logger.TransactionLogger;
import pool.TicketPool;
import threads.Customer;
import threads.Vendor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Simulation class orchestrates the ticketing system simulation.
 * It initializes the components, starts vendor and customer threads.
 */
public class Simulation {
    // Shared flag to signal if the simulation should stop
    private static volatile boolean stopRequested = false;

    public static void main(String[] args) {

        // Initialize the system configuration and scanner for user input
        Configuration config = SystemInitializer.initializeSystem();
        Scanner scanner = new Scanner(System.in);

        // Initialize simulation components
        TransactionLogger logger = new TransactionLogger();
        TicketPool ticketPool = new TicketPool(config, logger);
        List<Thread> vendorThreads = new ArrayList<>();
        List<Thread> customerThreads = new ArrayList<>();

        // Ask for the number of vendors and customers with validation
        int numVendors = getPositiveInteger(scanner, "Enter the number of vendors: ");
        int numCustomers = getPositiveInteger(scanner, "Enter the number of customers: ");

        // Wait for the user to start the simulation
        System.out.println("Press 1 to start the simulation, or 2 to quit.");
        while (true) {
            int choice = scanner.nextInt();
            if (choice == 1) {
                break;  // Start simulation
            } else if (choice == 2) {
                System.out.println("Simulation exited before starting.");
                return; // Exit program
            } else {
                System.out.println("Invalid input. Press 1 to start or 2 to quit.");
            }
        }

        // Start Vendor Threads
        for (int i = 0; i < numVendors; i++) {
            Thread vendor = new Thread(new Vendor(ticketPool, config.getTicketReleaseRate(), "Vendor-" + (i + 1)));
            vendorThreads.add(vendor);
            vendor.start();
        }

        // Start Customer Threads
        for (int i = 0; i < numCustomers; i++) {
            Thread customer = new Thread(new Customer(ticketPool, "Customer-" + (i + 1), config.getCustomerRetrievalRate()));
            customerThreads.add(customer);
            customer.start();
        }

        // Create and start the control thread
        ControlRunnable controlRunnable = new ControlRunnable(scanner, ticketPool, vendorThreads, customerThreads);
        Thread controlThread = new Thread(controlRunnable);
        controlThread.start();

        // Wait for threads to finish
        try {
            // Wait for vendors to finish
            for (Thread vendor : vendorThreads) {
                vendor.join();
            }

            // Signal customers that no more tickets will be added
            ticketPool.signalNoMoreTickets();

            // Wait for customers to finish
            for (Thread customer : customerThreads) {
                customer.join();
            }

            // Notify user when transactions are complete
            if (!stopRequested && ticketPool.isSimulationStopped()) {
                System.out.println("All transactions are complete. Press 2 to quit and save.");
            }

            // Wait for the control thread to finish
            controlThread.join();

        } catch (InterruptedException e) {
            System.out.println("Simulation interrupted.");
        }

        // Save configurations and transactions
        saveSimulationData(config, logger);
    }

    /**
     * Prompts the user for a positive integer input and ensures the input is valid.
     *
     * @param scanner The scanner object to read user input.
     * @param prompt The message displayed to the user.
     * @return A positive integer entered by the user.
     */

    private static int getPositiveInteger(Scanner scanner, String prompt) {
        int number;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                number = scanner.nextInt();
                if (number > 0) {
                    return number;
                }
            } else {
                scanner.next(); // Clear invalid input
            }
            System.out.println("Invalid input. Please enter a number greater than 0.");
        }
    }

    /**
     * Method to save the simulation data.
     *
     * @param config The system configuration to save.
     * @param logger The transaction logger to save.
     */

    private static void saveSimulationData(Configuration config, TransactionLogger logger) {
        try {
            config.saveToFile();
            logger.saveToFile();
            System.out.println("Simulation stopped. Configurations and transactions saved.");
        } catch (IOException e) {
            System.out.println("Failed to save files.");
        }
    }
}
