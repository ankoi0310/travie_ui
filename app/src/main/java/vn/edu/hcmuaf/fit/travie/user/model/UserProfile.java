package vn.edu.hcmuaf.fit.travie.user.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.Gender;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.DateTimeUtil;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SafeParcelable.Class(creator = "CREATOR")
public class UserProfile implements Parcelable {
    private String username;
    private String email;
    private String phone;
    private String fullName;
    private Gender gender;
    private LocalDate birthday;
    private String avatar;

    protected UserProfile(Parcel in) {
        username = in.readString();
        email = in.readString();
        phone = in.readString();
        fullName = in.readString();
        gender = Gender.valueOf(in.readString());
        birthday = LocalDate.parse(in.readString(), DateTimeUtil.getDateTimeFormatter("dd-MM-yyyy"));
        avatar = in.readString();
    }

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
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
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(phone);
        dest.writeString(fullName);
        dest.writeString(gender.name());
        dest.writeString(birthday.format(DateTimeUtil.getDateTimeFormatter("dd-MM-yyyy")));
    }
}
