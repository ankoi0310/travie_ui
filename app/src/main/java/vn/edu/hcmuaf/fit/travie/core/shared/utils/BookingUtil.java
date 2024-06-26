package vn.edu.hcmuaf.fit.travie.core.shared.utils;

import java.util.Locale;

import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;

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

    public static String generateDescription(Invoice invoice) {
        String bookingTime = invoice.getModifiedDate().format(DateTimeUtil.getDateTimeFormatter("HH:mm, dd/MM/yyyy"));

        String description = "";
        switch (invoice.getBookingStatus()) {
            case SUCCESS:
                description = String.format(Locale.getDefault(), "Đặt phòng thành công vào %s. Chúc bạn có một trải nghiệm tuyệt vời!", bookingTime);
                break;
            case REJECTED:
                description = String.format(Locale.getDefault(), "Đặt phòng bị từ chối vào %s. Vui lòng thử lại sau!", bookingTime);
                break;
            case COMPLETED:
                description = String.format(Locale.getDefault(), "Đặt phòng hoàn thành vào %s. Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!", bookingTime);
                break;
            case CANCELLED:
                String reason = "";
                switch (invoice.getPaymentStatus()) {
                    case UNPAID:
                        reason = "chưa thanh toán.";
                        break;
                    case FAILED:
                        reason = "thanh toán thất bại.";
                        break;
                }
                description = String.format(Locale.getDefault(), "Đặt phòng bị hủy vào %s do %s", bookingTime, reason);
                break;
            default:
                description = "Đặt phòng đang chờ xử lý. Vui lòng đợi trong giây lát!";
                break;
        }

        return description;
    }
}
