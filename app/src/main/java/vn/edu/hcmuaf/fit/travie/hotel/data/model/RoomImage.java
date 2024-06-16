package vn.edu.hcmuaf.fit.travie.hotel.data.model;

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
public class RoomImage extends BaseModel {
    private String image;
    private RoomType roomType;

    public RoomImage(Parcel in) {
        super(in);
        image = in.readString();
        roomType = in.readParcelable(RoomType.class.getClassLoader());
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(image);
        dest.writeParcelable(roomType, flags);
    }

    public static final Creator<RoomImage> CREATOR = new Creator<RoomImage>() {
        @Override
        public RoomImage createFromParcel(Parcel in) {
            return new RoomImage(in);
        }

        @Override
        public RoomImage[] newArray(int size) {
            return new RoomImage[size];
        }
    };

    public static RoomImage demo1() {
        return new RoomImage("image", RoomType.demo1());
    }

    public static RoomImage demo2() {
        return new RoomImage("image", RoomType.demo2());
    }
}
