package vn.edu.hcmuaf.fit.travie.invoice.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.common.model.BaseModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends BaseModel {
    private String code;
    private int totalPrice;
    private String paymentMethod;
    private BookingStatus bookingStatus;
    private PaymentStatus paymentStatus;

    public enum BookingStatus {
        PENDING,
        CONFIRMED,
        REJECTED,
        COMPLETED,
        CANCELLED
    }

    public enum PaymentStatus {
        PAID,
        UNPAID,
        FAILED,
        REFUNDED,
    }
}
