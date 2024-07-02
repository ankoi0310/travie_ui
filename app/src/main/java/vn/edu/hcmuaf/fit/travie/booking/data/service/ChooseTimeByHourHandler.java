package vn.edu.hcmuaf.fit.travie.booking.data.service;

import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChooseTimeByHourHandler {
    private LocalTime startTime;
    private int hourAmount;
    private long maxHours;

    private static ChooseTimeByHourHandler instance;

    private ChooseTimeByHourHandler() {
    }

    public static ChooseTimeByHourHandler getInstance() {
        if (instance == null) {
            instance = new ChooseTimeByHourHandler();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}
