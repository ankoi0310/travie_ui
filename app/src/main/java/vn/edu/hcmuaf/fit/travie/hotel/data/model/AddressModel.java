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
public class AddressModel extends BaseModel {
    private String detail;
    private int wardId;
    private int districtId;
    private int provinceId;
    private String fullAddress;

    public AddressModel(Parcel in) {
        super(in);
        detail = in.readString();
        wardId = in.readInt();
        districtId = in.readInt();
        provinceId = in.readInt();
        fullAddress = in.readString();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(detail);
        dest.writeInt(wardId);
        dest.writeInt(districtId);
        dest.writeInt(provinceId);
        dest.writeString(fullAddress);
    }

    public static final Creator<AddressModel> CREATOR = new Creator<AddressModel>() {
        @Override
        public AddressModel createFromParcel(Parcel in) {
            return new AddressModel(in);
        }

        @Override
        public AddressModel[] newArray(int size) {
            return new AddressModel[size];
        }
    };
}
