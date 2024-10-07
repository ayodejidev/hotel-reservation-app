package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    // Static reference for Singleton pattern
    private static ReservationService reservationService = null;

    // Map to store rooms, using room ID as the key
    private Map<String, IRoom> roomMap;

    // List to store reservations
    private List<Reservation> reservations;

    // Private constructor for Singleton pattern
    private ReservationService() {
        this.roomMap = new HashMap<>();
        this.reservations = new ArrayList<>();
    }

    // Static method to get the single instance of ReservationService
    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    // Method to add a room to the roomMap
    public void addRoom(IRoom room) {
        roomMap.put(room.getRoomNumber(), room);
    }

    // Method to get a room by room ID
    public IRoom getARoom(String roomId) {
        return roomMap.get(roomId);
    }

    // Method to reserve a room
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(newReservation);
        return newReservation;
    }

    // Method to find available rooms between check-in and check-out dates
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>(roomMap.values());

        // Filter out rooms that are already reserved for the given date range
        for (Reservation reservation : reservations) {
            if (reservation.getCheckInDate().before(checkOutDate) && reservation.getCheckOutDate().after(checkInDate)) {
                availableRooms.remove(reservation.getRoom());
            }
        }

        return availableRooms;
    }

    // Method to get all reservations of a customer
    public Collection<Reservation> getCustomersReservation(Customer customer) {
        List<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : reservations) {
            if (reservation.getCustomer().equals(customer)) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }

    // Method to print all reservations
    public void printAllReservation() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation : reservations) {
                System.out.println(reservation);
            }
        }
    }

    // Method to get all rooms
    // In ReservationService class
    public Collection<IRoom> getAllRooms() {
        return roomMap.values();
    }

}
