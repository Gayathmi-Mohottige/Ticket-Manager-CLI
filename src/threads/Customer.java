package threads;

import pool.TicketPool;
import java.util.Random;

/**
 * Customer class simulates a customer retrieving tickets from the TicketPool.
 */

public class Customer implements Runnable {

    // Ticket pool shared between customer threads
    private TicketPool pool;

    // Customer's name
    private String name;

    // The rate at which the customer attempts to retrieve tickets, in milliseconds
    private int retrievalRate;

    // Random object used to generate random number
    private Random random;

    /**
     * Constructor to initialize the Customer object.
     *
     * @param pool The TicketPool from which the customer will retrieve tickets.
     * @param name The name of the customer.
     * @param retrievalRate The rate at which the customer retrieves tickets, in milliseconds.
     */
    public Customer(TicketPool pool, String name, int retrievalRate) {
        this.pool = pool;
        this.name = name;
        this.retrievalRate = retrievalRate;
        this.random = new Random();
    }

    /**
     * The run method that simulates the customer's ticket retrieval behavior.
     */

    @Override
    public void run() {

        // Run as long as the thread is not interrupted and the simulation is not stopped
        while (!Thread.currentThread().isInterrupted() && !pool.isSimulationStopped()) {
            int ticketsToBuy = random.nextInt(10) + 1; // Random number between 1 and 10

            // Attempt to remove tickets from the pool
            pool.removeTickets(name, ticketsToBuy);

            // Delay between ticket retrievals based on retrievalRate
            try {
                Thread.sleep(retrievalRate);
            } catch (InterruptedException e) {
                break;  // Break out of the loop
            }
        }
        System.out.println(name + " has stopped retrieving.");
    }
}
