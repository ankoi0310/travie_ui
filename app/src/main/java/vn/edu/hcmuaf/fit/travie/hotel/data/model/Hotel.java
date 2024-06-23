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
    private ArrayList<BookingType> bookingTypes;
    private @Nullable ArrayList<Room> rooms;
    private ArrayList<String> images;
    private ArrayList<Amenity> amenities;
    private HotelStatus status;
    private double averageMark;
    private double averageMarkClean;
    private double averageMarkAmenity;
    private double averageMarkService;
    private ArrayList<Review> reviews;

    public Hotel(String name, String introduction, int firstHours, int checkIn, int checkOut, boolean daily, int startHourly, int endHourly, boolean hourly, int startOvernight, int endOvernight, boolean overnight, int cancelBeforeHours, Address address, ArrayList<BookingType> bookingTypes, ArrayList<String> images, ArrayList<Amenity> amenities, HotelStatus status, double averageMark, double averageMarkClean, double averageMarkAmenity, double averageMarkService, ArrayList<Review> reviews) {
        this.name = name;
        this.introduction = introduction;
        this.firstHours = firstHours;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.daily = daily;
        this.startHourly = startHourly;
        this.endHourly = endHourly;
        this.hourly = hourly;
        this.startOvernight = startOvernight;
        this.endOvernight = endOvernight;
        this.overnight = overnight;
        this.cancelBeforeHours = cancelBeforeHours;
        this.address = address;
        this.bookingTypes = bookingTypes;
        this.rooms = null;
        this.images = images;
        this.amenities = amenities;
        this.status = status;
        this.averageMark = averageMark;
        this.averageMarkClean = averageMarkClean;
        this.averageMarkAmenity = averageMarkAmenity;
        this.averageMarkService = averageMarkService;
        this.reviews = reviews;
    }

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
        address = in.readParcelable(Address.class.getClassLoader());
        bookingTypes = in.createTypedArrayList(BookingType.CREATOR);
        rooms = in.createTypedArrayList(Room.CREATOR);
        images = in.createStringArrayList();
        amenities = in.createTypedArrayList(Amenity.CREATOR);
        status = HotelStatus.valueOf(in.readString());
        averageMark = in.readDouble();
        averageMarkClean = in.readDouble();
        averageMarkAmenity = in.readDouble();
        averageMarkService = in.readDouble();
        reviews = in.createTypedArrayList(Review.CREATOR);
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
        dest.writeParcelable(address, flags);
        dest.writeTypedList(bookingTypes);
        dest.writeTypedList(rooms);
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
