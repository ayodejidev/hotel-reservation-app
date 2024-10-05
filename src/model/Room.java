package model;

public class Room implements IRoom {
    private String roomNumber;  // Room number as a String
    private Double price;       // Room price as a Double
    private RoomType roomType;  // Room type (SINGLE, DOUBLE) as an enum

    // Constructor to initialize the Room object
    public Room(String roomNumber, RoomType roomType, Double price) {
        super();
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
    }

    // Implementing the methods from the IRoom interface

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return price == 0.0;  // might change to return null for now
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber +
                ", Room Type: " + roomType +
                ", Price: " + (price > 0 ? "$" + price : "Free");
    }
}
