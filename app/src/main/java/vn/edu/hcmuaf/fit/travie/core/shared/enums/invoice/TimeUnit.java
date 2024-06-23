package vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice;

import lombok.Getter;

@Getter
public enum TimeUnit {
    HOUR("giờ"),
    DAY("ngày"),
    OVERNIGHT("đêm");

    private final String label;

    TimeUnit(String label) {
        this.label = label;
    }
}
