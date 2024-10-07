package menu;

import api.HotelResource;
import model.IRoom;
import model.Reservation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {
    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        runMainMenu();
    }

    // Method to run the Main Menu
    public static void runMainMenu() {
        boolean keepRunning = true;

        while (keepRunning) {
            printMainMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    findAndReserveARoom();
                    break;
                case "2":
                    seeMyReservations();
                    break;
                case "3":
                    createAnAccount();
                    break;
                case "4":
                    AdminMenu.runAdminMenu(); // Call the Admin Menu when it's ready
                    break;
                case "5":
                    System.out.println("Exiting application. Goodbye!");
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Invalid input. Please choose a number between 1 and 5.");
            }
        }
    }

    // Print the Main Menu options
    private static void printMainMenu() {
        System.out.println("\n===== Main Menu =====");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.print("Please select an option: ");
    }

    // Option 1: Find and reserve a room
    private static void findAndReserveARoom() {
        try {
            System.out.print("Enter check-in date (dd/MM/yyyy): ");
            Date checkInDate = dateFormat.parse(scanner.nextLine());

            System.out.print("Enter check-out date (dd/MM/yyyy): ");
            Date checkOutDate = dateFormat.parse(scanner.nextLine());

            // Find available rooms
            var availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
            if (availableRooms.isEmpty()) {
                System.out.println("No available rooms for the selected dates.");
            } else {
                System.out.println("Available Rooms:");
                for (IRoom room : availableRooms) {
                    System.out.println(room);
                }

                System.out.print("Enter room number to reserve: ");
                String roomNumber = scanner.nextLine();
                IRoom room = hotelResource.getRoom(roomNumber);

                if (room == null) {
                    System.out.println("Room not found.");
                    return;
                }

                System.out.print("Enter your email (example: name@domain.com): ");
                String customerEmail = scanner.nextLine();

                // Make the reservation
                Reservation reservation = hotelResource.bookARoom(customerEmail, room, checkInDate, checkOutDate);
                if (reservation != null) {
                    System.out.println("Reservation successful: " + reservation);
                } else {
                    System.out.println("Unable to make a reservation. Please check your email or create an account.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid date format or input.");
        }
    }

    // Option 2: See my reservations
    private static void seeMyReservations() {
        System.out.print("Enter your email (example: name@domain.com): ");
        String customerEmail = scanner.nextLine();

        var reservations = hotelResource.getCustomersReservations(customerEmail);
        if (reservations == null || reservations.isEmpty()) {
            System.out.println("No reservations found for the email provided.");
        } else {
            System.out.println("Your Reservations:");
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    // Option 3: Create an account
    private static void createAnAccount() {
        System.out.print("Enter your email (example: name@domain.com): ");
        String email = scanner.nextLine();

        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        hotelResource.createACustomer(email, firstName, lastName);
        System.out.println("Account created successfully.");
    }
}
