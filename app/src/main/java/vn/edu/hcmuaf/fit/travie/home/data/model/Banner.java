package vn.edu.hcmuaf.fit.travie.home.data.model;

import android.os.Parcel;

import androidx.annotation.NonNull;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.hcmuaf.fit.travie.core.common.model.BaseModel;

@Data
@EqualsAndHashCode(callSuper = true)
public class Banner extends BaseModel {
    private String title;
    private String description;
    private String image;
    private String url;

    protected Banner(Parcel in) {
        super(in);
        title = in.readString();
        description = in.readString();
        image = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeString(image);
        dest.writeString(url);
    }
}
