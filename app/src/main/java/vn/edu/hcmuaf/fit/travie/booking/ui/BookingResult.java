package vn.edu.hcmuaf.fit.travie.booking.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.booking.data.model.LinkCreationResponse;

@Getter
@RequiredArgsConstructor
public class BookingResult {
    private final LinkCreationResponse success;
    private final String error;
}