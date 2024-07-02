package vn.edu.hcmuaf.fit.travie.booking.ui.choosetime;

import java.time.LocalDate;

public class ContinuousSelectionHelper {
    public static DateSelection getSelection(LocalDate clickedDate, DateSelection dateSelection) {
        if (dateSelection.getStartDate() != null) {
            if (clickedDate.isBefore(dateSelection.getStartDate()) || dateSelection.getEndDate() != null) {
                return new DateSelection(clickedDate, null);
            } else if (!clickedDate.isEqual(dateSelection.getStartDate())) {
                return new DateSelection(dateSelection.getStartDate(), clickedDate);
            } else {
                return new DateSelection(clickedDate, null);
            }
        } else {
            return new DateSelection(clickedDate, null);
        }
    }

    public static boolean isInDateBetweenSelection(LocalDate inDate, LocalDate startDate, LocalDate endDate) {
        if (startDate.getMonth() == endDate.getMonth()) return false;
        if (inDate.getMonth() == startDate.getMonth()) return true;
        LocalDate firstDateInThisMonth = inDate.plusMonths(1).withDayOfMonth(1);
        return isDateBetween(firstDateInThisMonth, startDate, endDate) && startDate != firstDateInThisMonth;
    }

    public static boolean isOutDateBetweenSelection(LocalDate outDate, LocalDate startDate, LocalDate endDate) {
        if (startDate.getMonth() == endDate.getMonth()) return false;
        if (outDate.getMonth() == endDate.getMonth()) return true;
        LocalDate lastDateInThisMonth = outDate.withDayOfMonth(1).minusDays(1);
        return isDateBetween(lastDateInThisMonth, startDate, endDate) && endDate != lastDateInThisMonth;
    }

    public static boolean isDateBetween(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}
