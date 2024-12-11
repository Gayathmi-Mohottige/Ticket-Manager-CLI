package threads;

import pool.TicketPool;
import java.util.Random;

/**
 * Vendor class simulates a vendor adding tickets to the TicketPool.
 */

public class Vendor implements Runnable {
    // Ticket pool shared between vendor threads
    private TicketPool pool;

    // The rate at which the vendor adds tickets, in milliseconds
    private int releaseRate;

    // Vendor's name
    private String name;


    // Random object used to generate random numbers for the number of tickets to add
    private Random random;

    /**
     * Constructor to initialize the Vendor object.
     *
     * @param pool The TicketPool where the vendor will add tickets.
     * @param releaseRate The rate at which the vendor releases tickets, in milliseconds.
     * @param name The name of the vendor.
     */
    public Vendor(TicketPool pool, int releaseRate, String name) {
        this.pool = pool;
        this.releaseRate = releaseRate;
        this.name = name;
        this.random = new Random();
    }

    /**
     * The run method that simulates the vendor's ticket production behavior.
     */
    @Override
    public void run() {

        // Run as long as the simulation is not stopped
        while (!pool.isSimulationStopped()) {
            int ticketsToAdd = random.nextInt(10) + 1; // Random number between 1 and 10

            // Attempt to add tickets to the pool
            boolean success = pool.addTickets(name, ticketsToAdd);
            if (!success) {
                break;  // Exit the simulation if no more tickets can be added
            }

            // Delay between ticket additions based on releaseRate
            try {
                Thread.sleep(releaseRate);
            } catch (InterruptedException e) {
                break;  // Break out of the loop
            }
        }
        System.out.println(name + " has stopped production.");
    }
}
