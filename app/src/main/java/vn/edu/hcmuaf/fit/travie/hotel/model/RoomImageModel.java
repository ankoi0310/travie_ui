package vn.edu.hcmuaf.fit.travie.hotel.model;

import android.os.Parcel;

import androidx.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.common.model.BaseModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomImageModel extends BaseModel {
    private String image;
    private RoomTypeModel roomType;

    public RoomImageModel(Parcel in) {
        super(in);
        image = in.readString();
        roomType = in.readParcelable(RoomTypeModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(image);
        dest.writeParcelable(roomType, flags);
    }

    public static final Creator<RoomImageModel> CREATOR = new Creator<RoomImageModel>() {
        @Override
        public RoomImageModel createFromParcel(Parcel in) {
            return new RoomImageModel(in);
        }

        @Override
        public RoomImageModel[] newArray(int size) {
            return new RoomImageModel[size];
        }
    };
}
