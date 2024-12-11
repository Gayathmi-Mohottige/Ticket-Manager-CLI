package config;

import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;


/**
 * SystemInitializer class is responsible for initializing the system configuration.
 * It either loads configuration from a file or prompts the user to enter configuration details.
 */

public class SystemInitializer {


    /**
     * Initializes the system by loading configuration from a file or prompting the user for input.
     *
     * @return The Configuration object containing the system settings.
     */
    public static Configuration initializeSystem() {
        Scanner scanner = new Scanner(System.in);
        Configuration config = null;

        System.out.println("Do you want load configuration from your last session?");
        String choice = scanner.nextLine();

        // If user chooses to load previous configuration, attempt to load from file
        if (choice.equalsIgnoreCase("yes")) {
            try {
                config = Configuration.loadFromFile();
                System.out.println("Configurations loaded successfully");
            } catch (FileNotFoundException e) {
                System.out.println("Configuration file not found. Starting a new configuration.");
            } catch (IOException e) {
                System.out.println("Failed to load configurations. Starting a new configuration.");
            }
        }

        // If configuration could not be loaded, prompt the user for new settings
        if (config == null) {
            config = new Configuration();

            // Prompt user for configuration details with validation
            while (true) {
                config.setTotalTickets(getPositiveInteger(scanner, "Enter total number of tickets: "));
                config.setMaxTicketCapacity(getPositiveInteger(scanner, "Enter maximum ticket capacity: "));

                // Ensure total tickets do not exceed max ticket capacity
                if (config.getTotalTickets() <= config.getMaxTicketCapacity()) {
                    break;
                }
                System.out.println("Total number of tickets cannot exceed maximum ticket capacity. Please re-enter.");
            }

            config.setTicketReleaseRate(getPositiveInteger(scanner, "Enter ticket release rate (in milliseconds): "));
            config.setCustomerRetrievalRate(getPositiveInteger(scanner, "Enter customer retrieval rate (in milliseconds): "));
        }

        return config;
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
}
