package vn.edu.hcmuaf.fit.travie.booking.ui.choosetime;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DateSelection {
    private LocalDate startDate;
    private LocalDate endDate;

    public Long getDaysBetween() {
        if (startDate == null || endDate == null) {
            return null;
        }

        return ChronoUnit.DAYS.between(startDate, endDate);
    }
}
