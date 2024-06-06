package vn.edu.hcmuaf.fit.travie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private AppRole role;
    private UserInfo userInfo;
}
