package vn.edu.hcmuaf.fit.travie.user.model;

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
public class UserProfileRequest {
    private String username;
    private String email;
    private String phone;
    private String fullName;
    private Gender gender;
    private LocalDate birthday;
}
