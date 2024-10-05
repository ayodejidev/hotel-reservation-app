package model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;


    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  // Format dates to be more readable
        return "Reservation: " +
                "\nCustomer: " + customer +
                "\nRoom: " + room +
                "\nCheck-in Date: " + dateFormat.format(checkInDate) +
                "\nCheck-out Date: " + dateFormat.format(checkOutDate);
    }
}
