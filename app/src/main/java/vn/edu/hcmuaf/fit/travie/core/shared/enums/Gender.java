package vn.edu.hcmuaf.fit.travie.core.shared.enums;

import androidx.annotation.StringRes;

import vn.edu.hcmuaf.fit.travie.R;

public enum Gender {
    MALE, FEMALE, OTHER, PREFER_NOT_TO_SAY;

    @StringRes
    public int getStringRes() {
        switch (this) {
            case MALE:
                return R.string.male;
            case FEMALE:
                return R.string.female;
            case OTHER:
                return R.string.other;
            case PREFER_NOT_TO_SAY:
            default:
                return R.string.prefer_not_to_say;
        }
    }

    public static Gender fromStringRes(int res) {
        if (res == R.string.male) {
            return MALE;
        } else if (res == R.string.female) {
            return FEMALE;
        } else if (res == R.string.other) {
            return OTHER;
        } else {
            return PREFER_NOT_TO_SAY;
        }
    }
}
