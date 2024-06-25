package vn.edu.hcmuaf.fit.travie.core.common.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseModel implements Parcelable {
    private long id;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    protected BaseModel(Parcel in) {
        id = in.readLong();
        createdDate = LocalDateTime.parse(in.readString());
        modifiedDate = LocalDateTime.parse(in.readString());
    }

    public static final Creator<BaseModel> CREATOR = new Creator<BaseModel>() {
        @Override
        public BaseModel createFromParcel(Parcel in) {
            return new BaseModel(in);
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(createdDate.toString());
        dest.writeString(modifiedDate.toString());
    }
}
