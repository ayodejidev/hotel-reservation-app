package app;

import menu.AdminMenu;
import menu.MainMenu;

import java.util.Scanner;

public class HotelApplication {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        runApplication();
    }

    // Method to run the Hotel Reservation Application
    public static void runApplication() {
        boolean keepRunning = true;

        while (keepRunning) {
            printMainApplicationMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> AdminMenu.runAdminMenu();  // Admin option selected
                case "2" -> MainMenu.runMainMenu();  // Main Menu option selected
                case "3" -> {
                    keepRunning = false;
                    System.out.println("Exiting application. See you next time!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid input. Please choose a number between 1 and 3.");
            }
        }
    }

    // Print the Main Application Menu options
    private static void printMainApplicationMenu() {
        System.out.println("\n-- Welcome to Hotel Reservation App --");
        System.out.println("1. Admin Menu");
        System.out.println("2. Main Menu");
        System.out.println("3. Exit Application");
        System.out.print("Please select an option: ");
    }
}
