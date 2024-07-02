package vn.edu.hcmuaf.fit.travie.booking.data.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.PaymentMethod;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;

@Getter
@Setter
@AllArgsConstructor
public class BookingRequest {
    private Room room;
    private BookingType bookingType;
    private String guestName;
    private String guestPhone;
    private int totalPrice;
    private int finalPrice;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private PaymentMethod paymentMethod;

    private static BookingRequest instance;

    private BookingRequest() {}

    public static BookingRequest getInstance() {
        if (instance == null) {
            instance = new BookingRequest();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
