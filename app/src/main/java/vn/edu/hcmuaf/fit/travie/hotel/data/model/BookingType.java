package vn.edu.hcmuaf.fit.travie.hotel.data.model;

import android.os.Parcel;

import androidx.annotation.NonNull;

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

    protected BookingType(Parcel in) {
        super(in);
        name = in.readString();
        description = in.readString();
        startTime = LocalTime.parse(in.readString());
        endTime = LocalTime.parse(in.readString());
        unit = TimeUnit.valueOf(in.readString());
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(startTime.toString());
        dest.writeString(endTime.toString());
        dest.writeString(unit.name());
    }

    public static final Creator<BookingType> CREATOR = new Creator<BookingType>() {
        @Override
        public BookingType createFromParcel(Parcel in) {
            return new BookingType(in);
        }

        @Override
        public BookingType[] newArray(int size) {
            return new BookingType[size];
        }
    };

    public static BookingType demo1() {
        return new BookingType("Theo giờ", "Đặt phòng theo giờ", LocalTime.of(6, 0), LocalTime.of(22, 0), TimeUnit.HOUR);
    }

    public static BookingType demo2() {
        return new BookingType("Theo ngày", "Đặt phòng theo ngày", LocalTime.of(14, 0), LocalTime.of(0, 0), TimeUnit.DAY);
    }

    public static BookingType demo3() {
        return new BookingType("Qua đêm", "Đặt phòng qua đêm", LocalTime.of(22, 0), LocalTime.of(12, 0), TimeUnit.NIGHT);
    }

    public static List<BookingType> listDemo() {
        List<BookingType> bookingTypes = new ArrayList<>();
        bookingTypes.add(demo1());
        bookingTypes.add(demo2());
        bookingTypes.add(demo3());
        return bookingTypes;
    }

    public enum TimeUnit {
        HOUR, DAY, NIGHT
    }
}
