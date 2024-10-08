package api;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static AdminResource adminResource = null;

    // Get instances of CustomerService and ReservationService
    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    // Private constructor for Singleton pattern
    private AdminResource() {}

    // Static method to get the single instance of AdminResource
    public static AdminResource getInstance() {
        if (adminResource == null) {
            adminResource = new AdminResource();
        }
        return adminResource;
    }

    // Method to get a customer by email
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    // Method to add multiple rooms
    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }

    // Method to get all rooms
    public Collection<IRoom> getAllRooms() {
        return reservationService.getAllRooms();
    }

    // Method to get all customers
    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Method to display all reservations
    public void displayAllReservations() {
        reservationService.printAllReservation();
    }
}
