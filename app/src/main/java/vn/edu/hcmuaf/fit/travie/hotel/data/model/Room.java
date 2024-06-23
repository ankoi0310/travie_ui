package vn.edu.hcmuaf.fit.travie.hotel.data.model;

import android.os.Parcel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
    private List<Amenity> amenities;
    private List<String> images;
    private int status;
    private @Nullable Hotel hotel;

    public Room(String name, int numOfRooms, int firstHoursOrigin, int minNumHours, int maxNumHours, int oneDayOrigin, int overnightOrigin, int additionalHours, int additionalOrigin, boolean hasDiscount, boolean applyFlashSale, int priceFlashSale, boolean hasExtraFee, boolean available, boolean availableTonight, boolean availableTomorrow, boolean soldOut, List<Amenity> amenities, List<String> images, int status) {
        this.name = name;
        this.numOfRooms = numOfRooms;
        this.firstHoursOrigin = firstHoursOrigin;
        this.minNumHours = minNumHours;
        this.maxNumHours = maxNumHours;
        this.oneDayOrigin = oneDayOrigin;
        this.overnightOrigin = overnightOrigin;
        this.additionalHours = additionalHours;
        this.additionalOrigin = additionalOrigin;
        this.hasDiscount = hasDiscount;
        this.applyFlashSale = applyFlashSale;
        this.priceFlashSale = priceFlashSale;
        this.hasExtraFee = hasExtraFee;
        this.available = available;
        this.availableTonight = availableTonight;
        this.availableTomorrow = availableTomorrow;
        this.soldOut = soldOut;
        this.amenities = amenities;
        this.images = images;
        this.status = status;
        this.hotel = null;
    }

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
        amenities = in.createTypedArrayList(Amenity.CREATOR);
        images = in.createStringArrayList();
        status = in.readInt();
        hotel = in.readParcelable(Hotel.class.getClassLoader());
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
        dest.writeTypedList(amenities);
        dest.writeStringList(images);
        dest.writeInt(status);
        dest.writeParcelable(hotel, flags);
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
