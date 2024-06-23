package vn.edu.hcmuaf.fit.travie.user.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.user.model.ChangePasswordRequest;
import vn.edu.hcmuaf.fit.travie.user.model.UserProfile;
import vn.edu.hcmuaf.fit.travie.user.model.UserProfileRequest;

public interface UserService {
    @GET("user/profile")
    Call<HttpResponse<UserProfile>> getProfile();

    @PUT("user/change-password")
    Call<HttpResponse<String>> changePassword(@Body ChangePasswordRequest request);

    @PUT("user/profile")
    Call<HttpResponse<UserProfile>> updateProfile(@Body UserProfileRequest request);
}
