package vn.edu.hcmuaf.fit.travie.core.shared.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant;

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

    public static List<String> generateTimeList(LocalTime startTime, LocalTime endTime) {
        List<String> timeList = new ArrayList<>();
        while (startTime.isBefore(endTime)) {
            timeList.add(startTime.format(getDateTimeFormatter("HH:mm")));
            startTime = startTime.plusMinutes(30);

            // check startTime is exist in timeList or not
            if (timeList.contains(startTime.format(getDateTimeFormatter("HH:mm")))) {
                break;
            }
        }
        return timeList;
    }

    public static LocalTime roundUpToNearestHalfHour(LocalTime time) {
        int minute = time.getMinute();
        if (minute == 0 || minute == 30) {
            return time;
        }

        if (minute < 30) {
            return time.withMinute(30);
        }

        return time.plusHours(1).withMinute(0);
    }

    public static List<String> generateHourList(int i) {
        List<String> hours = new ArrayList<>();
        for (int j = 1; j <= i; j++) {
            hours.add(String.format(Locale.getDefault(), "%d" + AppConstant.HOUR_SUFFIX, j));
        }
        return hours;
    }

    public static boolean isSameDay(Calendar calendar1, Calendar calendar2) {
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
                calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH);
    }

    public static long calculateDuration(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.HOURS.between(start, end);
    }
}
