package vn.edu.hcmuaf.fit.travie.auth.domain.repository;

import vn.edu.hcmuaf.fit.travie.auth.data.model.LoggedInUser;
import vn.edu.hcmuaf.fit.travie.auth.data.model.LoginRequest;
import vn.edu.hcmuaf.fit.travie.core.handler.Result;
import vn.edu.hcmuaf.fit.travie.core.handler.error.DataError;

public interface AuthenticationRepository {
    Result<LoggedInUser, DataError> login(LoginRequest loginRequest);
    void logout();
}
