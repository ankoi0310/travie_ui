package vn.edu.hcmuaf.fit.travie.user.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.hcmuaf.fit.travie.user.domain.model.UserModel.UserProfile;

interface UserApi {
    @GET("/api/user/profile")
    Call<UserProfile> getUserProfile();
}
