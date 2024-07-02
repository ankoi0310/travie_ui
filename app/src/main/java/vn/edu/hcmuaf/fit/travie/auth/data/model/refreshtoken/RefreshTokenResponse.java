package vn.edu.hcmuaf.fit.travie.auth.data.model.refreshtoken;

import lombok.Data;

@Data
public class RefreshTokenResponse {
    private String accessToken;
    private String refreshToken;
}