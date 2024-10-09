package api;

import model.Customer;
import model.IRoom;
import model.Reservation;
import service.CustomerService;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;

public class HotelResource {

    private static HotelResource hotelResource = null;

    // Get instances of CustomerService and ReservationService
    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    // Private constructor for Singleton pattern
    private HotelResource() {}

    // Static method to get the single instance of HotelResource
    public static HotelResource getInstance() {
        if (hotelResource == null) {
            hotelResource = new HotelResource();
        }
        return hotelResource;
    }

    // Method to get a customer by email
    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    // Method to create a customer
    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    // Method to get a room by room number
    public IRoom getRoom(String roomNumber) {
        return reservationService.getARoom(roomNumber);
    }

    // Method to book a room
    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate) {
        Customer customer = customerService.getCustomer(customerEmail);
        if (customer != null) {
            return reservationService.reserveARoom(customer, room, checkInDate, checkOutDate);
        } else {
            System.out.println("Customer not found.");
            return null;
        }
    }

    // Method to get all reservations for a specific customer by email
    public Collection<Reservation> getCustomersReservations(String customerEmail) {
        Customer customer = customerService.getCustomer(customerEmail);
        if (customer != null) {
            return reservationService.getCustomersReservation(customer);
        } else {
            System.out.println("Customer not found.");
            return null;
        }
    }

    // Method to find available rooms between check-in and check-out dates
    public Collection<IRoom> findARoom(Date checkInDate, Date checkOutDate) {
        return reservationService.findRooms(checkInDate, checkOutDate);
    }

    // Method to find recommended rooms with a 7-day shift if no rooms are available
    public Collection<IRoom> findRecommendedRooms(Date checkInDate, Date checkOutDate) {
        return reservationService.findRecommendedRooms(checkInDate, checkOutDate);
    }
}
