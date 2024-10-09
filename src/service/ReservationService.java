package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ReservationService {
    private static ReservationService reservationService = null;

    // Thread-safe data stores for rooms and reservations
    private final Map<String, IRoom> roomMap;
    private final List<Reservation> reservations;

    private ReservationService() {
        this.roomMap = new ConcurrentHashMap<>();
        this.reservations = new CopyOnWriteArrayList<>();
    }

    public static ReservationService getInstance() {
        if (reservationService == null) {
            reservationService = new ReservationService();
        }
        return reservationService;
    }

    // Method to add a room to the roomMap
    public void addRoom(IRoom room) {
        if (room == null || roomMap.containsKey(room.getRoomNumber())) {
            System.out.println("Invalid room or room already exists.");
            return;
        }
        roomMap.put(room.getRoomNumber(), room);
    }

    // Method to get a room by room ID
    public IRoom getARoom(String roomId) {
        return roomMap.get(roomId);
    }

    // Method to reserve a room
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        if (customer == null || room == null || checkInDate == null || checkOutDate == null) {
            System.out.println("Invalid reservation details.");
            return null;
        }

        // Check if the room is available for the given date range
        if (!isRoomAvailable(room, checkInDate, checkOutDate)) {
            System.out.println("Room is already reserved during the requested date range.");
            return null;
        }

        Reservation newReservation = new Reservation(customer, room, checkInDate, checkOutDate);
        reservations.add(newReservation);
        return newReservation;
    }

    // Check if a room is available for the given date range
    private boolean isRoomAvailable(IRoom room, Date checkInDate, Date checkOutDate) {
        return reservations.stream()
                .filter(reservation -> reservation.getRoom().equals(room))
                .noneMatch(reservation -> datesOverlap(checkInDate, checkOutDate, reservation.getCheckInDate(), reservation.getCheckOutDate()));
    }

    // Method to find available rooms between check-in and check-out dates
    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        return roomMap.values().stream()
                .filter(room -> isRoomAvailable(room, checkInDate, checkOutDate))
                .collect(Collectors.toList());
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
        if (customer == null) {
            return Collections.emptyList();
        }

        return reservations.stream()
                .filter(reservation -> reservation.getCustomer().equals(customer))
                .collect(Collectors.toList());
    }

    // Method to cancel a reservation (to used in future iterations)
    public boolean cancelReservation(Reservation reservation) {
        if (reservation == null) {
            System.out.println("Invalid reservation.");
            return false;
        }

        return reservations.remove(reservation);
    }

    // Method to get all reservations
    public Collection<Reservation> getAllReservations() {
        return new ArrayList<>(reservations);
    }

    // Method to get all rooms
    public Collection<IRoom> getAllRooms() {
        return roomMap.values();
    }
}
