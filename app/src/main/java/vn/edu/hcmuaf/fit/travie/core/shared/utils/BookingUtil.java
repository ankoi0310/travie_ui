package vn.edu.hcmuaf.fit.travie.core.shared.utils;

import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;

public class BookingUtil {
    public static void calculateTotalPrice(BookingRequest bookingRequest) {
        int price = 0;

        switch (bookingRequest.getBookingType().getUnit()) {
            case HOUR:
                // Calculate amount of hours
                int hours = bookingRequest.getCheckOut().getHour() - bookingRequest.getCheckIn().getHour();

                // Price is first hour origin price + (hours - 1) * next hour origin price
                int firstHourOrigin = bookingRequest.getRoom().getFirstHoursOrigin();
                int firstHours = bookingRequest.getRoom().getMinNumHours();
                int additionalOrigin = bookingRequest.getRoom().getAdditionalOrigin();
                int additionalHours = bookingRequest.getRoom().getAdditionalHours(); // each additional hours will be charged with additionalOrigin price
                if (hours < firstHours) {
                    price = firstHourOrigin;
                    break;
                }

                int numberOfAdditionalHours = (int) (Math.floor((double) (hours - firstHours) / additionalHours));
                if (numberOfAdditionalHours == 0) {
                    price = firstHourOrigin + (hours - firstHours) * additionalOrigin;
                    break;
                }

                int additionalPrice = numberOfAdditionalHours * additionalOrigin;
                price = firstHourOrigin + additionalPrice;
                break;
            case OVERNIGHT:
                // Just 1 night
                price = bookingRequest.getRoom().getOvernightOrigin();
                break;
            case DAY:
                // Calculate amount of days
                int days = bookingRequest.getCheckOut().getDayOfYear() - bookingRequest.getCheckIn().getDayOfYear();
                price = bookingRequest.getRoom().getOneDayOrigin() * days;
                break;
        }

        bookingRequest.setTotalPrice(price);
        bookingRequest.setFinalPrice(price);
    }
}
