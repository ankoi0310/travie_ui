package vn.edu.hcmuaf.fit.travie.core.shared.utils;

import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static DateTimeFormatter getSimpleDateFormat() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    }

    public static DateTimeFormatter getSimpleDateFormat(String pattern) {
        if (pattern == null) {
            return getSimpleDateFormat();
        }
        return DateTimeFormatter.ofPattern(pattern);
    }
}
