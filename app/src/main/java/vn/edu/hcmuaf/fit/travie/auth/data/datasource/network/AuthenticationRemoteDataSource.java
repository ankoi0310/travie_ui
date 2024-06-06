package vn.edu.hcmuaf.fit.travie.auth.data.datasource.network;

import androidx.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Call;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.auth.data.model.LoggedInUser;
import vn.edu.hcmuaf.fit.travie.auth.data.model.LoginRequest;
import vn.edu.hcmuaf.fit.travie.auth.data.service.AuthenticationService;
import vn.edu.hcmuaf.fit.travie.core.handler.Result;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.handler.error.DataError;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;

@Singleton
public class AuthenticationRemoteDataSource {
    private final AuthenticationService authenticationService;

    @Inject
    public AuthenticationRemoteDataSource(RetrofitService retrofitService) {
        this.authenticationService = retrofitService.createService(AuthenticationService.class, null);
    }

    public Result<LoggedInUser, DataError> login(LoginRequest loginRequest) {
        try {
            final HttpResponse<LoggedInUser>[] httpResponse = new HttpResponse[1];
            authenticationService.login(loginRequest).enqueue(new Callback<HttpResponse<LoggedInUser>>() {
                @Override
                public void onResponse(@NonNull Call<HttpResponse<LoggedInUser>> call, Response<HttpResponse<LoggedInUser>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        httpResponse[0] = response.body();
                    } else {
                        httpResponse[0] = HttpResponse.<LoggedInUser>builder().success(false).message(response.message()).code(response.code()).build();
                    }
                }

                @Override
                public void onFailure(Call<HttpResponse<LoggedInUser>> call, Throwable t) {
                    httpResponse[0] = HttpResponse.<LoggedInUser>builder().success(false).message(t.getMessage()).code(500).build();
                }
            });

            if (!httpResponse[0].isSuccess()) {
                return new Result.Error<>(DataError.NETWORK.UNKNOWN);
            }
            return new Result.Success<>(httpResponse[0].getData());
        } catch (HttpException e) {
            switch (e.code()) {
                case 401:
                    return new Result.Error<>(DataError.NETWORK.UNAUTHORIZED);
                case 403:
                    return new Result.Error<>(DataError.NETWORK.FORBIDDEN);
                case 404:
                    return new Result.Error<>(DataError.NETWORK.NOT_FOUND);
                case 500:
                    return new Result.Error<>(DataError.NETWORK.INTERNAL_SERVER_ERROR);
                default:
                    return new Result.Error<>(DataError.NETWORK.UNKNOWN);
            }
        }
    }

    public void logout() {
        // TODO implement here
    }
}
