package vn.edu.hcmuaf.fit.travie.auth.data.repository;


import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.auth.data.datasource.network.AuthenticationRemoteDataSource;
import vn.edu.hcmuaf.fit.travie.auth.data.model.LoggedInUser;
import vn.edu.hcmuaf.fit.travie.auth.data.model.LoginRequest;
import vn.edu.hcmuaf.fit.travie.auth.domain.repository.AuthenticationRepository;
import vn.edu.hcmuaf.fit.travie.core.handler.Result;
import vn.edu.hcmuaf.fit.travie.core.handler.error.DataError;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class AuthenticationRepositoryImpl implements AuthenticationRepository {
    private final AuthenticationRemoteDataSource authenticationRemoteDataSource;

    @Override
    public Result<LoggedInUser, DataError> login(LoginRequest loginRequest) {
        return authenticationRemoteDataSource.login(loginRequest);
    }

    @Override
    public void logout() {

    }
}
