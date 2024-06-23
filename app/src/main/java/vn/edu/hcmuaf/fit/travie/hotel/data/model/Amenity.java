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
public class Amenity extends BaseModel {
    private String name;
    private boolean deleted;

    public Amenity(Parcel in) {
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

    public static final Creator<Amenity> CREATOR = new Creator<Amenity>() {
        @Override
        public Amenity createFromParcel(Parcel in) {
            return new Amenity(in);
        }

        @Override
        public Amenity[] newArray(int size) {
            return new Amenity[size];
        }
    };

    public static Amenity demo1() {
        return new Amenity("name", false);
    }

    public static Amenity demo2() {
        return new Amenity("name", false);
    }
}
