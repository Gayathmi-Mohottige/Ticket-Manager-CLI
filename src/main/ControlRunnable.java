package main;

import pool.TicketPool;

import java.util.List;
import java.util.Scanner;

/**
 * ControlRunnable class manages the control flow for stopping the simulation.
 * It listens for user input to stop the simulation and notifies all vendor and customer threads
 */

public class ControlRunnable implements Runnable {

    // Scanner object to read user input
    private final Scanner scanner;

    // Ticket pool instance
    private final TicketPool ticketPool;

    // List of vendor threads
    private final List<Thread> vendorThreads;

    // List of customer threads
    private final List<Thread> customerThreads;

    // Volatile flag to signal whether to stop the simulation
    private static volatile boolean stopRequested = false;

    /**
     * Constructor to initialize the ControlRunnable object.
     *
     * @param scanner The scanner object used for user input.
     * @param ticketPool The TicketPool instance.
     * @param vendorThreads The list of vendor threads.
     * @param customerThreads The list of customer threads.
     */
    public ControlRunnable(Scanner scanner, TicketPool ticketPool, List<Thread> vendorThreads, List<Thread> customerThreads) {
        this.scanner = scanner;
        this.ticketPool = ticketPool;
        this.vendorThreads = vendorThreads;
        this.customerThreads = customerThreads;
    }

    /**
     * The run method that listens for user input to stop the simulation.
     * If the user enters "2", it will stop the simulation and interrupt all vendor and customer threads.
     */

    @Override
    public void run() {
        System.out.println("Press 2 at any time to stop the simulation.");
        while (!stopRequested) {
            int userInput = scanner.nextInt();

            // Check if the user requested to stop the simulation
            if (userInput == 2) {
                stopRequested = true; // Signal to stop
                ticketPool.signalNoMoreTickets(); // Notify threads to stop gracefully

                // Interrupt all vendor and customer threads
                for (Thread vendor : vendorThreads) {
                    vendor.interrupt();
                }
                for (Thread customer : customerThreads) {
                    customer.interrupt();
                }
                break;
            } else {
                System.out.println("Invalid input. Press 2 to stop the simulation.");
            }
        }
    }
}

