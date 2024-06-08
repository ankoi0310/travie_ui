package vn.edu.hcmuaf.fit.travie.auth.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import vn.edu.hcmuaf.fit.travie.auth.model.LoginResponse;
import vn.edu.hcmuaf.fit.travie.auth.model.LoginRequest;
import vn.edu.hcmuaf.fit.travie.auth.model.RefreshTokenRequest;
import vn.edu.hcmuaf.fit.travie.auth.model.RefreshTokenResponse;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;

public interface AuthService {
    @POST("auth/login")
    Call<HttpResponse<LoginResponse>> login(@Body LoginRequest loginRequest);

    @POST("auth/refresh-token")
    Call<HttpResponse<RefreshTokenResponse>> refreshToken(@Body RefreshTokenRequest refreshTokenRequest);
}
