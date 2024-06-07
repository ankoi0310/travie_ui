package vn.edu.hcmuaf.fit.travie.auth.data.datasource.network;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.auth.data.model.LoginResponse;
import vn.edu.hcmuaf.fit.travie.auth.data.model.LoginRequest;
import vn.edu.hcmuaf.fit.travie.auth.data.service.AuthenticationService;
import vn.edu.hcmuaf.fit.travie.core.handler.Result;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.handler.error.DataError;
import vn.edu.hcmuaf.fit.travie.core.handler.ResultCallback;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;

@Singleton
public class AuthenticationRemoteDataSource {
    private final AuthenticationService authenticationService;

    @Inject
    public AuthenticationRemoteDataSource(RetrofitService retrofitService) {
        this.authenticationService = retrofitService.createService(AuthenticationService.class, null);
    }

    public void login(LoginRequest loginRequest, final ResultCallback<LoginResponse, DataError> callback) {
        Call<HttpResponse<LoginResponse>> call = authenticationService.login(loginRequest);
        call.enqueue(new Callback<HttpResponse<LoginResponse>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<LoginResponse>> call, @NonNull Response<HttpResponse<LoginResponse>> response) {
                Log.d("LoginResponse", response.toString());
                if (!response.isSuccessful()) {
                    callback.onComplete(new Result.Error<>(DataError.NETWORK.UNKNOWN));
                }

                if (response.body() == null || !response.body().isSuccess() || response.body().getData() == null) {
                    callback.onComplete(new Result.Error<>(DataError.NETWORK.UNKNOWN));
                }

                Log.d("LoginResponse", response.body().getData().getAccessToken());
                callback.onComplete(new Result.Success<>(response.body().getData()));
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<LoginResponse>> call, @NonNull Throwable throwable) {
                Log.d("LoginResponse", Objects.requireNonNull(throwable.getMessage()));
                callback.onComplete(new Result.Error<>(DataError.NETWORK.UNKNOWN));
            }
        });
    }

    public void logout() {
        // TODO implement here
    }
}
