package model;

public interface IRoom {
    // Returns the room number as a String
    String getRoomNumber();
    // Returns the price of the room as a Double
    Double getRoomPrice();
    // Returns the type of the room (SINGLE, DOUBLE) using the RoomType enum
    RoomType getRoomType();
    // Returns true if the room is free (price is 0.0), false otherwise
    boolean isFree();
}
