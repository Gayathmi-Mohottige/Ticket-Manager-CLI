package model;

/**
 * The Transaction class represents a single transaction in the system,
 * containing details such as the type of transaction, the name of the entity
 * involved, the number of tickets processed, and the remaining tickets.
 */

public class Transaction {

    // The type of the transaction
    private String type;

    // The name of the entity involved in the transaction
    private String name;

    // The number of tickets in this transaction
    private int numOfTickets;

    // The remaining tickets in the system after this transaction
    private int remainingTickets;

    /**
     * Constructor for the Transaction class.
     * Initializes a new Transaction object with the specified details.
     *
     * @param type The type of the transaction.
     * @param name The name of the entity involved.
     * @param numOfTickets The number of tickets in the transaction.
     * @param remainingTickets The number of tickets remaining in the system after the transaction.
     */

    public Transaction(String type, String name, int numOfTickets, int remainingTickets) {
        this.type = type;
        this.name = name;
        this.numOfTickets = numOfTickets;
        this.remainingTickets = remainingTickets;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfTickets() {
        return numOfTickets;
    }

    public void setNumOfTickets(int numOfTickets) {
        this.numOfTickets = numOfTickets;
    }

    public int getRemainingTickets() {
        return remainingTickets;
    }

    public void setRemainingTickets(int remainingTickets) {
        this.remainingTickets = remainingTickets;
    }

    /**
     * Returns a string representation of the Transaction object,
     * including all its attributes.
     *
     * @return a string summarizing the transaction details
     */

    @Override
    public String toString() {
        return "Transaction{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", numOfTickets=" + numOfTickets +
                ", remainingTickets=" + remainingTickets +
                '}';
    }
}
