package vn.edu.hcmuaf.fit.travie.core.shared.utils;

import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    public static DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    }

    public static DateTimeFormatter getDateTimeFormatter(String pattern) {
        if (pattern == null) {
            return getDateTimeFormatter();
        }
        return DateTimeFormatter.ofPattern(pattern);
    }
}
