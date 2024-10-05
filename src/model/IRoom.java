package model;

public interface IRoom {
    // Returns the room number as a String
    public String getRoomNumber();
    // Returns the price of the room as a Double
    public Double getRoomPrice();
    // Returns the type of the room (SINGLE, DOUBLE) using the RoomType enum
    public RoomType getRoomType();
    // Returns true if the room is free (price is 0.0), false otherwise
    public boolean isFree();
}
