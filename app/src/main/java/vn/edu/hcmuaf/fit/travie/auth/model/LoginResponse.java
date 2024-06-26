package vn.edu.hcmuaf.fit.travie.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String welcomeName;
    private String accessToken;
    private String refreshToken;
    private String tokenType;
}