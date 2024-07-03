package vn.edu.hcmuaf.fit.travie.user.data.model;


import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.Gender;

@Getter
@Setter
@AllArgsConstructor
public class UserProfileRequest {
    private String nickname;
    private String email;
    private String phone;
    private Gender gender;
    private LocalDate birthday;

    private static UserProfileRequest instance;

    private UserProfileRequest() {
    }

    public static UserProfileRequest getInstance() {
        if (instance == null) {
            instance = new UserProfileRequest();
        }
        return instance;
    }

    public static void clearInstance() {
        instance = null;
    }
}
