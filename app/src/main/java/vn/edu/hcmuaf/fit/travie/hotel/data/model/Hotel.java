package vn.edu.hcmuaf.fit.travie.hotel.data.model;

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.common.model.BaseModel;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.hotel.HotelStatus;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel extends BaseModel {
    private String name;
    private String introduction;
    private int firstHours;
    private int checkIn;
    private int checkOut;
    private boolean daily;
    private int startHourly;
    private int endHourly;
    private boolean hourly;
    private int startOvernight;
    private int endOvernight;
    private boolean overnight;
    private int cancelBeforeHours;
    private Address address;
    private ArrayList<BookingType> bookingTypes = new ArrayList<>();
    private @Nullable ArrayList<Room> rooms;
    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<Amenity> amenities = new ArrayList<>();
    private HotelStatus status;
    private double averageMark;
    private double averageMarkClean;
    private double averageMarkAmenity;
    private double averageMarkService;
    private ArrayList<Review> reviews = new ArrayList<>();

    public Hotel(Parcel in) {
        super(in);
        name = in.readString();
        introduction = in.readString();
        firstHours = in.readInt();
        checkIn = in.readInt();
        checkOut = in.readInt();
        daily = in.readByte() != 0;
        startHourly = in.readInt();
        endHourly = in.readInt();
        hourly = in.readByte() != 0;
        startOvernight = in.readInt();
        endOvernight = in.readInt();
        overnight = in.readByte() != 0;
        cancelBeforeHours = in.readInt();
        address = in.readTypedObject(Address.CREATOR);
        in.readTypedList(bookingTypes, BookingType.CREATOR);

        boolean hasRooms = in.readBoolean();
        if (hasRooms) {
            rooms = new ArrayList<>();
            in.readTypedList(rooms, Room.CREATOR);
        } else {
            rooms = null;
        }

        in.readStringList(images);
        in.readTypedList(amenities, Amenity.CREATOR);
        status = HotelStatus.valueOf(in.readString());
        averageMark = in.readDouble();
        averageMarkClean = in.readDouble();
        averageMarkAmenity = in.readDouble();
        averageMarkService = in.readDouble();
        in.readTypedList(reviews, Review.CREATOR);
    }

    public static final Creator<Hotel> CREATOR = new Creator<Hotel>() {
        @Override
        public Hotel createFromParcel(Parcel in) {
            return new Hotel(in);
        }

        @Override
        public Hotel[] newArray(int size) {
            return new Hotel[size];
        }
    };

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeString(introduction);
        dest.writeInt(firstHours);
        dest.writeInt(checkIn);
        dest.writeInt(checkOut);
        dest.writeByte((byte) (daily ? 1 : 0));
        dest.writeInt(startHourly);
        dest.writeInt(endHourly);
        dest.writeByte((byte) (hourly ? 1 : 0));
        dest.writeInt(startOvernight);
        dest.writeInt(endOvernight);
        dest.writeByte((byte) (overnight ? 1 : 0));
        dest.writeInt(cancelBeforeHours);
        dest.writeTypedObject(address, PARCELABLE_WRITE_RETURN_VALUE);
        dest.writeTypedList(bookingTypes);
        if (rooms != null) {
            dest.writeBoolean(true);
            dest.writeTypedList(rooms);
        } else {
            dest.writeBoolean(false);
        }
        dest.writeStringList(images);
        dest.writeTypedList(amenities);
        dest.writeString(status.name());
        dest.writeDouble(averageMark);
        dest.writeDouble(averageMarkClean);
        dest.writeDouble(averageMarkAmenity);
        dest.writeDouble(averageMarkService);
        dest.writeTypedList(reviews);
    }
}
