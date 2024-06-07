package vn.edu.hcmuaf.fit.travie.hotel.data.model;

import android.os.Parcel;

import androidx.annotation.NonNull;

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
public class HotelModel extends BaseModel {
    private String name;
    private String introduction;
    private AddressModel address;
    private List<BookingTypeModel> bookingTypes;
    private List<RoomModel> rooms;
    private List<String> images;
    private List<AmenityModel> amenities;
    private HotelStatus status;
    private double rating;
    private List<ReviewModel> reviews;

    public HotelModel(Parcel in) {
        super(in);
        name = in.readString();
        introduction = in.readString();
        address = in.readParcelable(AddressModel.class.getClassLoader());
        bookingTypes = in.createTypedArrayList(BookingTypeModel.CREATOR);
        rooms = in.createTypedArrayList(RoomModel.CREATOR);
        images = in.createStringArrayList();
        amenities = in.createTypedArrayList(AmenityModel.CREATOR);
        status = HotelStatus.valueOf(in.readString());
        rating = in.readDouble();
        reviews = in.createTypedArrayList(ReviewModel.CREATOR);
    }

    public static final Creator<HotelModel> CREATOR = new Creator<HotelModel>() {
        @Override
        public HotelModel createFromParcel(Parcel in) {
            return new HotelModel(in);
        }

        @Override
        public HotelModel[] newArray(int size) {
            return new HotelModel[size];
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
}
