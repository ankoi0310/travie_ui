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
public class AmenityModel extends BaseModel {
    private String name;
    private boolean deleted;

    public AmenityModel(Parcel in) {
        super(in);
        name = in.readString();
        deleted = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(name);
        dest.writeByte((byte) (deleted ? 1 : 0));
    }

    public static final Creator<AmenityModel> CREATOR = new Creator<AmenityModel>() {
        @Override
        public AmenityModel createFromParcel(Parcel in) {
            return new AmenityModel(in);
        }

        @Override
        public AmenityModel[] newArray(int size) {
            return new AmenityModel[size];
        }
    };
}
