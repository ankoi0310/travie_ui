package vn.edu.hcmuaf.fit.travie.auth.data.model.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String nickname;
    private String phone;
    private String avatar;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
}