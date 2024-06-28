package vn.edu.hcmuaf.fit.travie.hotel.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;

@Getter
@RequiredArgsConstructor
public class HotelResult {
    private final Hotel success;
    private final String error;
}
