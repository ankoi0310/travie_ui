package vn.edu.hcmuaf.fit.travie.auth.data.repository;


import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.auth.data.datasource.network.AuthenticationRemoteDataSource;
import vn.edu.hcmuaf.fit.travie.auth.data.model.LoginResponse;
import vn.edu.hcmuaf.fit.travie.auth.data.model.LoginRequest;
import vn.edu.hcmuaf.fit.travie.auth.domain.repository.AuthenticationRepository;
import vn.edu.hcmuaf.fit.travie.core.handler.error.DataError;
import vn.edu.hcmuaf.fit.travie.core.infrastructure.ResultCallback;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AuthenticationRepositoryImpl implements AuthenticationRepository {
    private final AuthenticationRemoteDataSource authenticationRemoteDataSource;

    @Override
    public void login(LoginRequest loginRequest,
                   final ResultCallback<LoginResponse, DataError> callback) {
        authenticationRemoteDataSource.login(loginRequest, callback);
    }

    @Override
    public void logout() {

    }
}
