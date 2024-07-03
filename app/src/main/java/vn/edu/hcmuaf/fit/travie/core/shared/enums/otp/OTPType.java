package vn.edu.hcmuaf.fit.travie.core.shared.enums.otp;

public enum OTPType {
    VERIFY_EMAIL,
    VERIFY_PHONE,
    RESET_PASSWORD;

    public static OTPType fromString(String str) {
        return valueOf(str);
    }
}
