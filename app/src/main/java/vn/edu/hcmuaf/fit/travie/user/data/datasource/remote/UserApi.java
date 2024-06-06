package vn.edu.hcmuaf.fit.travie.user.data.datasource.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.hcmuaf.fit.travie.user.data.model.UserModel.UserProfile;

interface UserApi {
    @GET("/api/user/profile")
    Call<UserProfile> getUserProfile();
}
