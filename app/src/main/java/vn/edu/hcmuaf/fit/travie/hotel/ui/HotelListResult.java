package vn.edu.hcmuaf.fit.travie.hotel.ui;

import java.util.ArrayList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;

@Getter
@RequiredArgsConstructor
public class HotelListResult {
    private final ArrayList<Hotel> success;
    private final String error;
}
