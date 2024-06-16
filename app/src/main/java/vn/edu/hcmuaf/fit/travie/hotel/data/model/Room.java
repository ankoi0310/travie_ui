package vn.edu.hcmuaf.fit.travie.hotel.data.model;

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.common.model.BaseModel;

@Getter
@Setter
@AllArgsConstructor
public class Room extends BaseModel {
    private String name;
    private String description;
    private int price;
    private List<Amenity> amenities;
    private List<RoomImage> roomImages;
    private @Nullable Hotel hotel;

    public Room(String name, String description, int price, List<Amenity> amenities, List<RoomImage> roomImages) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.amenities = amenities;
        this.roomImages = roomImages;
        this.hotel = null;
    }

    public Room(Parcel in) {
        super(in);
        name = in.readString();
        description = in.readString();
        price = in.readInt();
        amenities = in.createTypedArrayList(Amenity.CREATOR);
        roomImages = in.createTypedArrayList(RoomImage.CREATOR);
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeInt(price);
        dest.writeTypedList(amenities);
        dest.writeTypedList(roomImages);
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    public static Room demo1() {
        List<Amenity> amenities = new ArrayList<>();
        amenities.add(Amenity.demo1());
        amenities.add(Amenity.demo2());
        List<RoomImage> roomImages = new ArrayList<>();
        roomImages.add(RoomImage.demo1());
        roomImages.add(RoomImage.demo2());
        return new Room("Room 1", "description", 100000, amenities, roomImages, Hotel.demo1());
    }

    public static Room demo2() {
        List<Amenity> amenities = new ArrayList<>();
        amenities.add(Amenity.demo1());
        amenities.add(Amenity.demo2());
        List<RoomImage> roomImages = new ArrayList<>();
        roomImages.add(RoomImage.demo1());
        roomImages.add(RoomImage.demo2());
        return new Room("Room 2", "description", 100000, amenities, roomImages, Hotel.demo1());
    }
}
