package vn.edu.hcmuaf.fit.travie.auth.data.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import vn.edu.hcmuaf.fit.travie.auth.data.model.LoggedInUser;
import vn.edu.hcmuaf.fit.travie.auth.data.model.LoginRequest;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;

public interface AuthenticationService {
    @POST("auth/login")
    Call<HttpResponse<LoggedInUser>> login(@Body LoginRequest loginRequest);
}
