package vn.edu.hcmuaf.fit.travie.user.data.service;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;
import vn.edu.hcmuaf.fit.travie.user.data.model.ChangePasswordRequest;
import vn.edu.hcmuaf.fit.travie.user.data.model.UserProfile;
import vn.edu.hcmuaf.fit.travie.user.data.model.UserProfileRequest;

public interface UserService {
    @GET("user/profile")
    Call<HttpResponse<UserProfile>> getProfile();

    @PUT("user/change-password")
    Call<HttpResponse<String>> changePassword(@Body ChangePasswordRequest request);

    @GET("user/booking-history")
    Call<HttpResponse<ArrayList<Invoice>>> getBookingHistory();

    @PUT("user/profile")
    Call<HttpResponse<UserProfile>> updateProfile(@Body UserProfileRequest request);
}
