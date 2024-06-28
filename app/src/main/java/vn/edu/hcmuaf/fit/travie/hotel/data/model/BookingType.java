package vn.edu.hcmuaf.fit.travie.hotel.data.model;

import android.os.Parcel;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.common.model.BaseModel;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.TimeUnit;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.DateTimeUtil;

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

    public BookingType(Parcel in) {
        super(in);
        name = in.readString();
        description = in.readString();
        startTime = in.readSerializable(LocalTime.class.getClassLoader(), LocalTime.class);
        endTime = in.readSerializable(LocalTime.class.getClassLoader(), LocalTime.class);
        unit = TimeUnit.fromValue(in.readString());
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeSerializable(startTime);
        dest.writeSerializable(endTime);
        dest.writeString(unit.getValue());
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
}
