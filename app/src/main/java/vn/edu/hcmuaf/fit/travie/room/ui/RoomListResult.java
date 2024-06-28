package vn.edu.hcmuaf.fit.travie.room.ui;

import java.util.ArrayList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;

@Getter
@RequiredArgsConstructor
public class RoomListResult {
    private final ArrayList<Room> success;
    private final String error;
}
