package vn.edu.hcmuaf.fit.travie.hotel.data.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.common.model.BaseModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingType extends BaseModel {
    private String name;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private TimeUnit unit;

    public static List<BookingType> listDemo() {
        return new ArrayList<BookingType>() {{
            add(new BookingType("Theo giờ", "Đặt phòng theo giờ", LocalTime.of(6, 0), LocalTime.of(22, 0), TimeUnit.HOUR));
            add(new BookingType("Theo ngày", "Đặt phòng theo ngày", LocalTime.of(14, 0), LocalTime.of(0, 0), TimeUnit.DAY));
            add(new BookingType("Qua đêm", "Đặt phòng qua đêm", LocalTime.of(22, 0), LocalTime.of(12, 0), TimeUnit.NIGHT));
        }};
    }

    public enum TimeUnit {
        HOUR, DAY, NIGHT
    }
}
