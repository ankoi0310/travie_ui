package vn.edu.hcmuaf.fit.travie.room.ui;

import java.util.ArrayList;

import lombok.Getter;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Room;

@Getter
public class RoomListResult {
    private final ArrayList<Room> success;
    private final String error;

    public RoomListResult(ArrayList<Room> success, String error) {
        this.success = success;
        this.error = error;
    }
}
