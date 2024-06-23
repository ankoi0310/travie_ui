package vn.edu.hcmuaf.fit.travie.room.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;

@Getter
@RequiredArgsConstructor
public class RoomResult {
    private final Room success;
    private final String error;
}
