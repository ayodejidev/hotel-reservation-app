package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;

public class ReservationService {
    private static ReservationService reservationService = null;

    // Data stores for rooms and reservations
    private final Map<String, IRoom> roomMap;
    private final List<Reservation> reservations;

    private ReservationService() {
        this.roomMap = new HashMap<>();
        this.reservations = new ArrayList<>();
    }

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
        // Check for conflicting reservations
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(room) &&
                    datesOverlap(checkInDate, checkOutDate, reservation.getCheckInDate(), reservation.getCheckOutDate())) {
                System.out.println("Room is already reserved during the requested date range.");
                return null;
            }
        }

        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(newReservation);
        return newReservation;
    }

    // Method to find available rooms between check-in and check-out dates
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> availableRooms = new ArrayList<>(roomMap.values());

        // Filter out rooms that are already reserved for the given date range
        for (Reservation reservation : reservations) {
            if (datesOverlap(checkInDate, checkOutDate, reservation.getCheckInDate(), reservation.getCheckOutDate())) {
                availableRooms.remove(reservation.getRoom());
            }
        }

        return availableRooms;
    }

    // Method to find recommended rooms with a 7-day shift in check-in and check-out dates
    public Collection<IRoom> findRecommendedRooms(Date checkInDate, Date checkOutDate) {
        Calendar calendar = Calendar.getInstance();

        // Shift dates by 7 days for new search
        calendar.setTime(checkInDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date newCheckInDate = calendar.getTime();

        calendar.setTime(checkOutDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        Date newCheckOutDate = calendar.getTime();

        // Search for available rooms with the shifted date range
        return findRooms(newCheckInDate, newCheckOutDate);
    }

    // Helper method to check if two date ranges overlap
    private boolean datesOverlap(Date checkIn1, Date checkOut1, Date checkIn2, Date checkOut2) {
        return checkIn1.before(checkOut2) && checkOut1.after(checkIn2);
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
