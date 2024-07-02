package vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice;

import androidx.annotation.StringRes;

import java.io.Serializable;

import lombok.Getter;
import vn.edu.hcmuaf.fit.travie.R;

@Getter
public enum TimeUnit implements Serializable {
    HOUR("hour", R.string.by_hour, "giờ"),
    DAY("day", R.string.by_day, "ngày"),
    OVERNIGHT("overnight", R.string.overnight, "đêm");

    private final String value;
    private final @StringRes int text;
    private final String suffix;

    TimeUnit(String value, int text, String suffix) {
        this.value = value;
        this.text = text;
        this.suffix = suffix;
    }

    public static TimeUnit fromValue(String value) {
        for (TimeUnit timeUnit : TimeUnit.values()) {
            if (timeUnit.value.equals(value)) {
                return timeUnit;
            }
        }
        return null;
    }
}
