package config;

import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Configuration class to manage the application's configurable parameters
 * such as ticket handling and rates. It includes functionality to save
 * and load the configuration from a JSON file.
 */

public class Configuration {

    // The name of the configuration file where data is saved
    private static final String CONFIG_FILENAME = "config.json";

    // Number of total tickets available in the system
    private int totalTickets;

    // The rate at which new tickets are released
    private int ticketReleaseRate;

    // The rate at which customers retrieve tickets
    private int customerRetrievalRate;

    // The maximum number of tickets the system can hold
    private int maxTicketCapacity;


    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }

    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }

    /**
     * Saves the current configuration to the config.json file.
     * Uses Gson to convert the configuration object to a JSON string
     * and writes it to the file.
     *
     * @throws IOException if there is an error writing to the file
     */

    public void saveToFile() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(CONFIG_FILENAME)) {
            gson.toJson(this, writer);  // Convert this Configuration object to JSON and save
        }
    }

    /**
     * Loads the configuration from the config.json file.
     * Uses the Gson library to read the JSON file and transform its content into a Configuration object.
     *
     * @return Configuration object populated with values from the file
     * @throws IOException if there is an error reading the file
     */

    public static Configuration loadFromFile() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileReader reader = new FileReader(CONFIG_FILENAME)) {
            return gson.fromJson(reader, Configuration.class);  // convert back the JSON content from the file into a Configuration object
        }
    }

}


