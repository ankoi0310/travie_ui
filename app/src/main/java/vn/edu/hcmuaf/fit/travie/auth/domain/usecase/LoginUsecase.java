package vn.edu.hcmuaf.fit.travie.auth.domain.usecase;

import javax.inject.Inject;

import lombok.Data;
import vn.edu.hcmuaf.fit.travie.auth.data.model.LoggedInUser;
import vn.edu.hcmuaf.fit.travie.auth.data.model.LoginRequest;
import vn.edu.hcmuaf.fit.travie.auth.domain.repository.AuthenticationRepository;
import vn.edu.hcmuaf.fit.travie.core.handler.Result;
import vn.edu.hcmuaf.fit.travie.core.handler.error.DataError;
import vn.edu.hcmuaf.fit.travie.core.usecase.UseCaseWithParams;

public class LoginUsecase extends UseCaseWithParams<LoggedInUser, LoginRequest> {
    private final AuthenticationRepository authenticationRepository;

    @Inject
    public LoginUsecase(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public Result<LoggedInUser, DataError> execute(LoginRequest loginRequest) {
        return authenticationRepository.login(loginRequest);
    }
}
