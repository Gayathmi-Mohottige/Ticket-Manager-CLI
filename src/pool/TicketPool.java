package pool;

import config.Configuration;
import logger.TransactionLogger;
import model.Transaction;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * TicketPool class manages a pool of tickets and allows ticket adding and retrieval operations.
 * It ensures thread-safe access to the ticket pool using synchronization
 */

public class TicketPool {

    // Queue to hold the tickets
    private Queue<Integer> ticketPool;

    // Configuration values for the ticket pool
    private int totalTickets;
    private int maxCapacity;
    private int ticketsProduced;

    // Logger to log transactions
    private TransactionLogger logger;


    // Flag to indicate if no more tickets can be added
    private boolean noMoreTickets;

    /**
     * Constructor to initialize the TicketPool.
     *
     * @param config The configuration object
     * @param logger The logger instance to log ticket transactions.
     */

    public TicketPool(Configuration config, TransactionLogger logger) {
        this.ticketPool = new ConcurrentLinkedQueue<>();
        this.totalTickets = config.getTotalTickets();
        this.maxCapacity = config.getMaxTicketCapacity();
        this.ticketsProduced = 0;
        this.logger = logger;
        this.noMoreTickets = false;
    }

    /**
     * Adds a specified number of tickets to the pool.
     *
     * @param vendorName The name of the vendor adding the tickets.
     * @param count The number of tickets to add (requested amount by vendor).
     * @return boolean indicating if the tickets were successfully added.
     */

    public synchronized boolean addTickets(String vendorName, int count) {

        // If no more tickets can be added or the max capacity is reached, return false
        if (noMoreTickets || ticketsProduced >= maxCapacity) {
            noMoreTickets = true; // Signal that no more tickets will be added
            return false;
        }

        // Calculate the number of tickets to add
        int ticketsToAdd = Math.min(count, maxCapacity - ticketsProduced);

        // Wait if adding the tickets would exceed the total number of available ticket
        while (ticketPool.size() + ticketsToAdd > totalTickets) {
            try {
                System.out.println("Ticket pool is full vendors are waiting...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupt status
                return false; // Exit if interrupted
            }
        }

        // Add tickets to the pool
        for (int i = 0; i < ticketsToAdd; i++) {
            ticketPool.add(1);
        }
        ticketsProduced += ticketsToAdd;
        System.out.println(vendorName + " added " + ticketsToAdd + " tickets.");

        // Log transaction
        logger.logTransaction(new Transaction("ADD", vendorName, ticketsToAdd, ticketPool.size()));

        // Mark no more tickets will be produced if max capacity is reached
        if (ticketsProduced >= maxCapacity) {
            noMoreTickets = true; // Mark as no more tickets will be produced
        }

        notifyAll(); // Notify all the waiting threads
        return true;
    }

    /**
     * Removes a specified number of tickets from the pool.
     *
     * @param customerName The name of the customer requesting the tickets.
     * @param count The number of tickets to remove.
     */

    public synchronized void removeTickets(String customerName, int count) {

        // Wait if there are not enough tickets in the pool
        while (ticketPool.size() < count) {
            if (noMoreTickets && ticketPool.isEmpty()) {
                return; // Exit if no more tickets will be produced and the pool is empty
            }
            try {
                System.out.println("Ticket pool is empty customers are waiting...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupt status
                return; // Exit if interrupted
            }
        }

        // Remove the tickets from the pool
        for (int i = 0; i < count; i++) {
            ticketPool.poll();
        }
        System.out.println(customerName + " purchased " + count + " tickets.");

        // Log transaction
        logger.logTransaction(new Transaction("RETRIEVE", customerName, count, ticketPool.size()));
        notifyAll();   // Notify all waiting threads
    }

    /**
     * Signals that no more tickets will be added to the pool.
     */
    public synchronized void signalNoMoreTickets() {
        noMoreTickets = true;
        notifyAll();
    }

    /**
     * Checks if the simulation is stopped.
     *
     * @return boolean indicating if no more tickets will be added and the pool is empty.
     */
    public boolean isSimulationStopped() {
        return noMoreTickets && ticketPool.isEmpty();
    }
}
