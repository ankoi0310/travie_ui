package vn.edu.hcmuaf.fit.travie.booking.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import vn.edu.hcmuaf.fit.travie.booking.data.model.LinkCreationResponse;

@Getter
@AllArgsConstructor
public class BookingResult {
    private LinkCreationResponse success;
    private String error;
}
