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
public class BookingTypeModel extends BaseModel {
    private String name;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private TimeUnit unit;

    protected BookingTypeModel(Parcel in) {
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

    public static final Creator<BookingTypeModel> CREATOR = new Creator<BookingTypeModel>() {
        @Override
        public BookingTypeModel createFromParcel(Parcel in) {
            return new BookingTypeModel(in);
        }

        @Override
        public BookingTypeModel[] newArray(int size) {
            return new BookingTypeModel[size];
        }
    };

    public static List<BookingTypeModel> listDemo() {
        return new ArrayList<BookingTypeModel>() {{
            add(new BookingTypeModel("Theo giờ", "Đặt phòng theo giờ", LocalTime.of(6, 0), LocalTime.of(22, 0), TimeUnit.HOUR));
            add(new BookingTypeModel("Theo ngày", "Đặt phòng theo ngày", LocalTime.of(14, 0), LocalTime.of(0, 0), TimeUnit.DAY));
            add(new BookingTypeModel("Qua đêm", "Đặt phòng qua đêm", LocalTime.of(22, 0), LocalTime.of(12, 0), TimeUnit.NIGHT));
        }};
    }

    public enum TimeUnit {
        HOUR, DAY, NIGHT
    }
}
