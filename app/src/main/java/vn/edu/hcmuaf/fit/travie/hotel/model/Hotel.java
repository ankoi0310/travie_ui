package vn.edu.hcmuaf.fit.travie.hotel.model;

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
public class Hotel extends BaseModel {
    private String name;
    private String introduction;
    private Address address;
    private List<BookingType> bookingTypes;
    private @Nullable List<Room> rooms;
    private List<String> images;
    private List<Amenity> amenities;
    private HotelStatus status;
    private double rating;
    private List<ReviewModel> reviews;

    public Hotel(Parcel in) {
        super(in);
        name = in.readString();
        introduction = in.readString();
        address = in.readParcelable(Address.class.getClassLoader());
        bookingTypes = in.createTypedArrayList(BookingType.CREATOR);
        rooms = in.createTypedArrayList(Room.CREATOR);
        images = in.createStringArrayList();
        amenities = in.createTypedArrayList(Amenity.CREATOR);
        status = HotelStatus.valueOf(in.readString());
        rating = in.readDouble();
        reviews = in.createTypedArrayList(ReviewModel.CREATOR);
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
        dest.writeStringList(images);
        dest.writeDouble(rating);
    }

    public enum HotelStatus {
        ACTIVE, INACTIVE, DELETED
    }

    public static Hotel demo1() {
        List<BookingType> bookingTypes = BookingType.listDemo();

        List<String> images = new ArrayList<>();
        images.add("https://firebasestorage.googleapis.com/v0/b/travie-8acf1.appspot.com/o/images%2Fhotels%2F118_1635947579_6182943b12a6d.jpg?alt=media&token=594cdede-043c-4933-bf63-60d6285991f5");
        images.add("https://firebasestorage.googleapis.com/v0/b/travie-8acf1.appspot.com/o/images%2Fhotels%2F6130_1677731099_6400251be7b1d.jpg?alt=media&token=151b6591-a001-4633-9de4-95ecf3842739");

        List<Amenity> amenities = new ArrayList<>();
        amenities.add(Amenity.demo1());
        amenities.add(Amenity.demo2());

        List<ReviewModel> reviews = new ArrayList<>();

        return new Hotel("Hotel 1", "Introduction", Address.demo(), bookingTypes, null, images,
                amenities, HotelStatus.ACTIVE, 4.5, reviews);
    }
}
