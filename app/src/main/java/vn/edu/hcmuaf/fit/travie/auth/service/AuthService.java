package vn.edu.hcmuaf.fit.travie.auth.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import vn.edu.hcmuaf.fit.travie.auth.model.ForgotPasswordRequest;
import vn.edu.hcmuaf.fit.travie.auth.model.LoginRequest;
import vn.edu.hcmuaf.fit.travie.auth.model.LoginResponse;
import vn.edu.hcmuaf.fit.travie.auth.model.RefreshTokenRequest;
import vn.edu.hcmuaf.fit.travie.auth.model.RefreshTokenResponse;
import vn.edu.hcmuaf.fit.travie.auth.model.RegisterRequest;
import vn.edu.hcmuaf.fit.travie.auth.model.RegisterResponse;
import vn.edu.hcmuaf.fit.travie.auth.model.ResetPasswordRequest;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;

public interface AuthService {
    @POST("auth/login")
    Call<HttpResponse<LoginResponse>> login(@Body LoginRequest loginRequest);
    @POST("auth/refresh-token")
    Call<HttpResponse<RefreshTokenResponse>> refreshToken(@Body RefreshTokenRequest refreshTokenRequest);
    @POST("auth/register")
    Call<HttpResponse<RegisterResponse>> register(@Body RegisterRequest registerRequest);
    @POST("auth/verify")
    Call<HttpResponse<String>> verify(@Query("code") String code);

    @POST("auth/forgot-password")
    Call<HttpResponse<String>> forgotPassword(@Body ForgotPasswordRequest forgotPasswordRequest);

    @POST("auth/reset-password")
    Call<HttpResponse<Void>> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);
}
