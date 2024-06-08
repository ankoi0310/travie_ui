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
public class RoomTypeModel extends BaseModel {
    private String name;
    private String description;

    public RoomTypeModel(Parcel in) {
        super(in);
        name = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeString(description);
    }

    public static final Creator<RoomTypeModel> CREATOR = new Creator<RoomTypeModel>() {
        @Override
        public RoomTypeModel createFromParcel(Parcel in) {
            return new RoomTypeModel(in);
        }

        @Override
        public RoomTypeModel[] newArray(int size) {
            return new RoomTypeModel[size];
        }
    };
}
