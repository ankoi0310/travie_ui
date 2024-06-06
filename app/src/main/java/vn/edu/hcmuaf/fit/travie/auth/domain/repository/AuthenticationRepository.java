package vn.edu.hcmuaf.fit.travie.auth.domain.repository;

import vn.edu.hcmuaf.fit.travie.auth.data.model.LoginResponse;
import vn.edu.hcmuaf.fit.travie.auth.data.model.LoginRequest;
import vn.edu.hcmuaf.fit.travie.core.handler.error.DataError;
import vn.edu.hcmuaf.fit.travie.core.infrastructure.ResultCallback;

public interface AuthenticationRepository {
    void login(LoginRequest loginRequest, final ResultCallback<LoginResponse, DataError> callback);
    void logout();
}
