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
public class Address extends BaseModel {
    private String detail;
    private int wardId;
    private int districtId;
    private int provinceId;
    private double latitude;
    private double longitude;
    private String fullAddress;

    public Address(Parcel in) {
        super(in);
        detail = in.readString();
        wardId = in.readInt();
        districtId = in.readInt();
        provinceId = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        fullAddress = in.readString();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(detail);
        dest.writeInt(wardId);
        dest.writeInt(districtId);
        dest.writeInt(provinceId);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(fullAddress);
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
