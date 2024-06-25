package vn.edu.hcmuaf.fit.travie.booking.data.service;

import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;

public class BookingRequestHolder {
    private static BookingRequestHolder instance;
    private BookingRequest bookingRequest;

    private BookingRequestHolder() {
        // Private constructor to prevent instantiation
    }

    public static BookingRequestHolder getInstance() {
        if (instance == null) {
            instance = new BookingRequestHolder();
        }
        return instance;
    }

    public BookingRequest getBookingRequest() {
        return bookingRequest;
    }

    public void setBookingRequest(BookingRequest bookingRequest) {
        this.bookingRequest = bookingRequest;
    }
}
