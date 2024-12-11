package logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Transaction;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * The TransactionLogger class is responsible for logging transactions
 * and saving them to a JSON file. It uses a synchronized approach to
 * ensure thread safety.
 */

public class TransactionLogger {

    // The name of the JSON file where transactions are stored
    private static final String TRANSACTION_FILENAME = "transactions.json";

    // A list to hold all transactions
    private List<Transaction> transactions;

    /**
     * Constructor for TransactionLogger.
     * Initializes an empty list to store transactions.
     */

    public TransactionLogger() {
        this.transactions = new ArrayList<>();
    }

    /**
     * Logs a transaction by adding it to the transaction list.
     *
     * @param transaction the transaction to be logged
     */

    public synchronized void logTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    /**
     * Saves all logged transactions to the JSON file.
     * The transactions are converted using Gson with pretty printing enabled.
     */

    public synchronized void saveToFile () {

        // Create a Gson instance
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Write the list of transactions to the JSON file
        try (FileWriter writer = new FileWriter(TRANSACTION_FILENAME)){
            gson.toJson(transactions, writer);  // convert the transactions list to JSON
        }catch (IOException e){
            // Handle exceptions during file writing
            System.out.println("Error writing to file" + e.getMessage());
        }
    }


}
