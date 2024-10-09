package menu;

import api.HotelResource;
import app.HotelApplication;
import model.IRoom;
import model.Reservation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Collection;
import java.util.Calendar;

public class MainMenu {

    private static final HotelResource hotelResource = HotelResource.getInstance();
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void runMainMenu() {
        boolean keepRunning = true;

        while (keepRunning) {
            printMainMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> findAndReserveARoom();
                case "2" -> seeMyReservations();
                case "3" -> createAnAccount();
                case "4" -> AdminMenu.runAdminMenu();  // Opens Admin Menu
                case "5" -> {
                    System.out.println("Exiting Main Menu...");
                    keepRunning = false;
                    HotelApplication.runApplication(); // Exits Main Menu and returns to Hotel Application Menu
                }
                default -> System.out.println("Invalid input. Please choose a number between 1 and 5.");
            }
        }
    }

    // Print the Main Menu options
    private static void printMainMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Find and reserve a room");
        System.out.println("2. See my reservations");
        System.out.println("3. Create an account");
        System.out.println("4. Admin");
        System.out.println("5. Exit");
        System.out.print("Please select an option: ");
    }

    // Option 1: Find and reserve a room with enhanced input validation
    private static void findAndReserveARoom() {
        try {
            Date checkInDate = promptForDate("Enter check-in date (dd/MM/yyyy): ");
            Date checkOutDate = promptForDate("Enter check-out date (dd/MM/yyyy): ");

            // Restrict booking on past dates
            Date currentDate = new Date();
            if (checkInDate.before(currentDate) || checkOutDate.before(currentDate)) {
                System.out.println("Reservation dates must be in the future. Please enter a valid date range.");
                return;
            }

            if (!checkOutDate.after(checkInDate)) {
                System.out.println("Check-out date must be after the check-in date.");
                return;
            }

            // Ask if the user wants to search for free or paid rooms
            System.out.print("Do you want to search for (1) Paid rooms or (2) Free rooms? Enter 1 or 2: ");
            String roomTypeChoice = scanner.nextLine();

            // Find available rooms
            Collection<IRoom> availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);

            // Filter rooms based on user input (paid rooms or free rooms)
            if (roomTypeChoice.equals("1")) {
                availableRooms = availableRooms.stream().filter(room -> room.getRoomPrice() > 0.0).collect(Collectors.toList());
            } else if (roomTypeChoice.equals("2")) {
                availableRooms = availableRooms.stream().filter(room -> room.getRoomPrice() == 0.0).collect(Collectors.toList());
            } else {
                System.out.println("Invalid choice. Please enter 1 for Paid rooms or 2 for Free rooms.");
                return;
            }

            if (availableRooms.isEmpty()) {
                // If no rooms are available, search for recommended rooms
                System.out.println("No available rooms for the selected dates. Searching for recommended rooms...");

                Collection<IRoom> recommendedRooms = hotelResource.findRecommendedRooms(checkInDate, checkOutDate);
                if (recommendedRooms.isEmpty()) {
                    System.out.println("No recommended rooms available for alternative dates.");
                    return;
                }

                // Calculate alternative dates
                Date newCheckInDate = addDaysToDate(checkInDate, 7);
                Date newCheckOutDate = addDaysToDate(checkOutDate, 7);

                System.out.println("Recommended Rooms (available from " + dateFormat.format(newCheckInDate) +
                        " to " + dateFormat.format(newCheckOutDate) + "):");
                for (IRoom room : recommendedRooms) {
                    System.out.println(room);
                }

                System.out.print("Enter room number to reserve for the alternative dates: ");
                String roomNumber = scanner.nextLine();
                IRoom room = hotelResource.getRoom(roomNumber);

                if (room == null) {
                    System.out.println("Room not found.");
                    return;
                }

                System.out.print("Enter your email (example: name@domain.com): ");
                String customerEmail = scanner.nextLine();

                Reservation reservation = hotelResource.bookARoom(customerEmail, room, newCheckInDate, newCheckOutDate);
                if (reservation != null) {
                    System.out.println("Reservation successful for the alternative dates: " + reservation);
                } else {
                    System.out.println("Unable to make a reservation. Please check your email or create an account.");
                }
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

                Reservation reservation = hotelResource.bookARoom(customerEmail, room, checkInDate, checkOutDate);
                if (reservation != null) {
                    System.out.println("Reservation successful: " + reservation);
                } else {
                    System.out.println("Unable to make a reservation. Please check your email or create an account.");
                }
            }
        } catch (ParseException e) {
            System.out.println("Error: Invalid date format. Please enter the date in 'dd/MM/yyyy' format.");
        }
    }

    // Helper method to add days to a date
    private static Date addDaysToDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    // Prompt the user for a valid date
    private static Date promptForDate(String promptMessage) throws ParseException {
        while (true) {
            System.out.print(promptMessage);
            String dateInput = scanner.nextLine();

            try {
                return dateFormat.parse(dateInput);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please enter the date in 'dd/MM/yyyy' format.");
            }
        }
    }

    // Option 2: See my reservations with email validation
    private static void seeMyReservations() {
        System.out.print("Enter your email (example: name@domain.com): ");
        String customerEmail = scanner.nextLine();

        if (isValidEmail(customerEmail)) {
            System.out.println("Invalid email format. Please enter a valid email.");
            return;
        }

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

    // Option 3: Create an account with email validation
    private static void createAnAccount() {
        System.out.print("Enter your email (example: name@domain.com): ");
        String email = scanner.nextLine();

        if (isValidEmail(email)) {
            System.out.println("Invalid email format. Please enter a valid email.");
            return;
        }

        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        hotelResource.createACustomer(email, firstName, lastName);
        System.out.println("Account created successfully.");
    }

    // Email validation helper method
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        return !email.matches(emailRegex);
    }
}
