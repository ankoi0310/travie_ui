package vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice;

import androidx.annotation.StringRes;

import vn.edu.hcmuaf.fit.travie.R;

public enum BookingStatus {
    PENDING,
    SUCCESS,
    REJECTED,
    COMPLETED,
    CANCELLED;

    public static @StringRes int getResId(BookingStatus status) {
        switch (status) {
            case SUCCESS:
                return R.string.booking_status_success;
            case REJECTED:
                return R.string.booking_status_rejected;
            case COMPLETED:
                return R.string.booking_status_completed;
            case CANCELLED:
                return R.string.booking_status_cancelled;
            default:
                return R.string.booking_status_pending;
        }
    }
}
