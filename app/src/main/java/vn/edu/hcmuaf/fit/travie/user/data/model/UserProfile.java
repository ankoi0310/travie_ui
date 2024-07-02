package vn.edu.hcmuaf.fit.travie.user.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.Gender;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile implements Parcelable {
    private String nickname;
    private String email;
    private String phone;
    private Gender gender;
    private LocalDate birthday;
    private String avatar;

    protected UserProfile(Parcel in) {
        nickname = in.readString();
        email = in.readString();
        phone = in.readString();
        gender = Gender.valueOf(in.readString());
        birthday = LocalDate.parse(in.readString());
        avatar = in.readString();
    }

    public static final Creator<UserProfile> CREATOR = new Creator<>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(nickname);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(gender.name());
        dest.writeString(birthday.toString());
    }
}
