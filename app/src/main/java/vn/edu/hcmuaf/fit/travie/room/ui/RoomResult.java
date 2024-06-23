package vn.edu.hcmuaf.fit.travie.room.ui;

import vn.edu.hcmuaf.fit.travie.hotel.data.model.Room;

public class RoomResult {
    private final Room success;
    private final String error;

    public RoomResult(Room success) {
        this.success = success;
        this.error = null;
    }

    public RoomResult(String error) {
        this.success = null;
        this.error = error;
    }
}
