package vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice;

import androidx.annotation.StringRes;

import vn.edu.hcmuaf.fit.travie.R;

public enum PaymentStatus {
    UNPAID,
    PAID,
    FAILED,
    REFUNDED;

    public static @StringRes int getResId(PaymentStatus status) {
        switch (status) {
            case PAID:
                return R.string.payment_status_paid;
            case FAILED:
                return R.string.payment_status_failed;
            case REFUNDED:
                return R.string.payment_status_refunded;
            default:
                return R.string.payment_status_unpaid;
        }
    }
}
