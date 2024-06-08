package vn.edu.hcmuaf.fit.travie.hotel.model;

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
public class RoomModel extends BaseModel {
    private String name;
    private String description;
    private int price;
    private List<AmenityModel> amenities;
    private List<RoomImageModel> roomImages;

    public RoomModel(Parcel in) {
        super(in);
        name = in.readString();
        description = in.readString();
        price = in.readInt();
        amenities = in.createTypedArrayList(AmenityModel.CREATOR);
        roomImages = in.createTypedArrayList(RoomImageModel.CREATOR);
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

    public static final Creator<RoomModel> CREATOR = new Creator<RoomModel>() {
        @Override
        public RoomModel createFromParcel(Parcel in) {
            return new RoomModel(in);
        }

        @Override
        public RoomModel[] newArray(int size) {
            return new RoomModel[size];
        }
    };
}
