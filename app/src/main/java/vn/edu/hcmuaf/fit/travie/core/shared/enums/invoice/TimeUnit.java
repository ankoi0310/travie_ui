package vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice;

import java.io.Serializable;

import lombok.Getter;

@Getter
public enum TimeUnit implements Serializable {
    HOUR("hour", "giờ"),
    DAY("day", "ngày"),
    OVERNIGHT("overnight", "đêm");

    private final String value;
    private final String label;

    TimeUnit(String value, String label) {
        this.value = value;
        this.label = label;
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
