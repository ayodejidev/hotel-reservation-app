package model;

public class FreeRoom extends Room {
    // Constructor to create a FreeRoom, setting the price to 0
    public FreeRoom(String roomNumber, RoomType roomType) {
        super(roomNumber, roomType, 0.0);  // Always set the price to 0 for FreeRoom
    }
}
