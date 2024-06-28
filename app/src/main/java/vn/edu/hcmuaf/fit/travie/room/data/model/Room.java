package vn.edu.hcmuaf.fit.travie.room.data.model;

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.common.model.BaseModel;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Amenity;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;

@Getter
@Setter
@AllArgsConstructor
public class Room extends BaseModel {
    private String name;
    private int numOfRooms;
    private int firstHoursOrigin;
    private int minNumHours;
    private int maxNumHours;
    private int oneDayOrigin;
    private int overnightOrigin;
    private int additionalHours;
    private int additionalOrigin;
    private boolean hasDiscount;
    private boolean applyFlashSale;
    private int priceFlashSale;
    private boolean hasExtraFee;
    private boolean available;
    private boolean availableTonight;
    private boolean availableTomorrow;
    private boolean soldOut;
    private ArrayList<Amenity> amenities = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();
    private int status;
    private @Nullable Hotel hotel;

    public Room(Parcel in) {
        super(in);
        name = in.readString();
        numOfRooms = in.readInt();
        firstHoursOrigin = in.readInt();
        minNumHours = in.readInt();
        maxNumHours = in.readInt();
        oneDayOrigin = in.readInt();
        overnightOrigin = in.readInt();
        additionalHours = in.readInt();
        additionalOrigin = in.readInt();
        hasDiscount = in.readByte() != 0;
        applyFlashSale = in.readByte() != 0;
        priceFlashSale = in.readInt();
        hasExtraFee = in.readByte() != 0;
        available = in.readByte() != 0;
        availableTonight = in.readByte() != 0;
        availableTomorrow = in.readByte() != 0;
        soldOut = in.readByte() != 0;
        in.readParcelableList(amenities, Amenity.class.getClassLoader(), Amenity.class);
        in.readStringList(images);
        status = in.readInt();

        boolean hasHotel = in.readBoolean();
        if (hasHotel) {
            hotel = in.readTypedObject(Hotel.CREATOR);
        } else {
            hotel = null;
        }
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeInt(numOfRooms);
        dest.writeInt(firstHoursOrigin);
        dest.writeInt(minNumHours);
        dest.writeInt(maxNumHours);
        dest.writeInt(oneDayOrigin);
        dest.writeInt(overnightOrigin);
        dest.writeInt(additionalHours);
        dest.writeInt(additionalOrigin);
        dest.writeByte((byte) (hasDiscount ? 1 : 0));
        dest.writeByte((byte) (applyFlashSale ? 1 : 0));
        dest.writeInt(priceFlashSale);
        dest.writeByte((byte) (hasExtraFee ? 1 : 0));
        dest.writeByte((byte) (available ? 1 : 0));
        dest.writeByte((byte) (availableTonight ? 1 : 0));
        dest.writeByte((byte) (availableTomorrow ? 1 : 0));
        dest.writeByte((byte) (soldOut ? 1 : 0));
        dest.writeParcelableList(amenities, flags);
        dest.writeStringList(images);
        dest.writeInt(status);

        if (hotel != null) {
            dest.writeBoolean(true);
            dest.writeTypedObject(hotel, PARCELABLE_WRITE_RETURN_VALUE);
        } else {
            dest.writeBoolean(false);
        }
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
}
