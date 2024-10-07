package app;

import menu.AdminMenu;
import menu.MainMenu;

import java.util.Scanner;

public class HotelReservationApp {
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
                case "1":
                    // Admin option selected
                    AdminMenu.runAdminMenu();
                    break;
                case "2":
                    // Customer option selected
                    MainMenu.runMainMenu();
                    break;
                case "3":
                    // Exit option selected
                    System.out.println("Exiting application. Goodbye!");
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid input. Please choose a number between 1 and 3.");
            }
        }
    }

    // Print the Main Application Menu options
    private static void printMainApplicationMenu() {
        System.out.println("\n===== Hotel Reservation System =====");
        System.out.println("1. Admin");
        System.out.println("2. Customer");
        System.out.println("3. Exit Application");
        System.out.print("Please select an option: ");
    }
}
