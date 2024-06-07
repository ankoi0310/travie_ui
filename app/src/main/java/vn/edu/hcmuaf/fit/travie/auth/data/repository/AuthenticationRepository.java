package vn.edu.hcmuaf.fit.travie.auth.data.repository;


import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Provides;
import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.auth.data.datasource.network.AuthenticationRemoteDataSource;
import vn.edu.hcmuaf.fit.travie.auth.data.model.LoginResponse;
import vn.edu.hcmuaf.fit.travie.auth.data.model.LoginRequest;
import vn.edu.hcmuaf.fit.travie.core.handler.error.DataError;
import vn.edu.hcmuaf.fit.travie.core.handler.ResultCallback;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AuthenticationRepository {
    private final AuthenticationRemoteDataSource authenticationRemoteDataSource;

    public void login(LoginRequest loginRequest,
                   final ResultCallback<LoginResponse, DataError> callback) {
        authenticationRemoteDataSource.login(loginRequest, callback);
    }

    public void logout() {

    }
}
