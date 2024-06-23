package vn.edu.hcmuaf.fit.travie.booking.data.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.PaymentMethod;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private Room room;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private BookingType bookingType;
    private String guestName;
    private String guestPhone;
    private int totalPrice;
    private PaymentMethod paymentMethod;
}
