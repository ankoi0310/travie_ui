package vn.edu.hcmuaf.fit.travie.user.service;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.user.model.UserProfile;

public interface UserService {
    @GET("user/profile")
    Call<HttpResponse<UserProfile>> getProfile();
}
