package vn.edu.hcmuaf.fit.travie.auth.data.service;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import vn.edu.hcmuaf.fit.travie.auth.data.model.login.LoginRequest;
import vn.edu.hcmuaf.fit.travie.auth.data.model.login.LoginResponse;
import vn.edu.hcmuaf.fit.travie.auth.data.model.refreshtoken.RefreshTokenResponse;
import vn.edu.hcmuaf.fit.travie.auth.data.model.register.RegisterRequest;
import vn.edu.hcmuaf.fit.travie.auth.data.model.forgotpassword.ResetPasswordRequest;
import vn.edu.hcmuaf.fit.travie.auth.data.model.register.RegisterResponse;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;

public interface AuthService {
    @POST("auth/check-email")
    Call<HttpResponse<String>> checkEmail(@Query("email") String email);

    @Multipart
    @POST("auth/register")
    Call<HttpResponse<RegisterResponse>> register(@Part MultipartBody.Part avatar, @Part("request") RequestBody request);

    @POST("auth/verify")
    Call<HttpResponse<String>> verify(@Query("code") String code);

    @POST("auth/forgot-password")
    Call<HttpResponse<String>> forgotPassword(@Query("email") String email);

    @POST("auth/reset-password")
    Call<HttpResponse<Void>> resetPassword(@Body ResetPasswordRequest resetPasswordRequest);

    @POST("auth/login")
    Call<HttpResponse<LoginResponse>> login(@Body LoginRequest loginRequest);

    @POST("auth/login/facebook")
    Call<HttpResponse<LoginResponse>> loginFacebook(@Query("accessToken") String accessToken);

    @POST("auth/refresh-token")
    Call<HttpResponse<RefreshTokenResponse>> refreshToken(@Query("token") String token);
}
