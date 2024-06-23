package vn.edu.hcmuaf.fit.travie.booking.ui.choosetime;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;

@Getter
@Setter
@AllArgsConstructor
public class ChooseTimeResult {
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private BookingType bookingType;
}
