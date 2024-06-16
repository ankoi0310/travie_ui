package vn.edu.hcmuaf.fit.travie.hotel.data.model;

import android.os.Parcel;
import android.os.Parcelable;

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
public class Review extends BaseModel {
    private AppUserReview user;
    private int rating;
    private String comment;

    public Review(Parcel in) {
        super(in);
        user = in.readParcelable(AppUserReview.class.getClassLoader());
        rating = in.readInt();
        comment = in.readString();
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(user, flags);
        dest.writeInt(rating);
        dest.writeString(comment);
    }

    public static final Creator<Review> CREATOR = new Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AppUserReview implements Parcelable {
        private String username;
        private String avatar;

        protected AppUserReview(Parcel in) {
            username = in.readString();
            avatar = in.readString();
        }

        public static final Creator<AppUserReview> CREATOR = new Creator<AppUserReview>() {
            @Override
            public AppUserReview createFromParcel(Parcel in) {
                return new AppUserReview(in);
            }

            @Override
            public AppUserReview[] newArray(int size) {
                return new AppUserReview[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(@NonNull Parcel dest, int flags) {
            dest.writeString(username);
            dest.writeString(avatar);
        }
    }
}
