package vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice;

import lombok.Getter;
import vn.edu.hcmuaf.fit.travie.R;

@Getter
public enum PaymentMethod {
    MOMO(R.string.payment_method_momo),
    ATM(R.string.payment_method_atm);

    private final int labelResId;

    PaymentMethod(int labelResId) {
        this.labelResId = labelResId;
    }

    public static PaymentMethod fromResId(int resId) {
        if (resId == R.id.rbMomo) {
            return MOMO;
        } else if (resId == R.id.rbATM) {
            return ATM;
        }

        return null;
    }
}
