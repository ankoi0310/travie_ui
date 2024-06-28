package vn.edu.hcmuaf.fit.travie.auth.model;

import lombok.Data;

@Data
public class RefreshTokenResponse {
    private String accessToken;
    private String refreshToken;
}
