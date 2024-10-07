package menu;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminMenu {
    private static final AdminResource adminResource = AdminResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);

    // Method to run the Admin Menu
    public static void runAdminMenu() {
        boolean keepRunning = true;

        while (keepRunning) {
            printAdminMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayAllCustomers();
                    break;
                case "2":
                    displayAllRooms();
                    break;
                case "3":
                    displayAllReservations();
                    break;
                case "4":
                    addRoom();
                    break;
                case "5":
                    keepRunning = false; // Go back to Main Menu
                    break;
                default:
                    System.out.println("Invalid input. Please choose a number between 1 and 5.");
            }
        }
    }

    // Print the Admin Menu options
    private static void printAdminMenu() {
        System.out.println("\n===== Admin Menu =====");
        System.out.println("1. See all Customers");
        System.out.println("2. See all Rooms");
        System.out.println("3. See all Reservations");
        System.out.println("4. Add a Room");
        System.out.println("5. Back to Main Menu");
        System.out.print("Please select an option: ");
    }

    // Option 1: Display all customers
    private static void displayAllCustomers() {
        var customers = adminResource.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("All Customers:");
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
    }

    // Option 2: Display all rooms
    private static void displayAllRooms() {
        var rooms = adminResource.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            System.out.println("All Rooms:");
            for (IRoom room : rooms) {
                System.out.println(room);
            }
        }
    }

    // Option 3: Display all reservations
    private static void displayAllReservations() {
        System.out.println("All Reservations:");
        adminResource.displayAllReservations();
    }

    // Option 4: Add a room
    private static void addRoom() {
        System.out.print("Enter room number: ");
        String roomNumber = scanner.nextLine();

        System.out.print("Enter room price: ");
        double roomPrice = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter room type (1 for SINGLE, 2 for DOUBLE): ");
        String roomTypeInput = scanner.nextLine();
        RoomType roomType = null;

        if (roomTypeInput.equals("1")) {
            roomType = RoomType.SINGLE;
        } else if (roomTypeInput.equals("2")) {
            roomType = RoomType.DOUBLE;
        } else {
            System.out.println("Invalid room type selection.");
            return;
        }

        IRoom room = new Room(roomNumber, roomType, roomPrice);

        List<IRoom> roomsToAdd = new ArrayList<>();
        roomsToAdd.add(room);
        adminResource.addRoom(roomsToAdd);

        System.out.println("Room added successfully.");
    }
}
