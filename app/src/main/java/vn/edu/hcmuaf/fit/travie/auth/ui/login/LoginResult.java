package vn.edu.hcmuaf.fit.travie.auth.ui.login;

import vn.edu.hcmuaf.fit.travie.auth.data.model.login.LoginResponse;

public record LoginResult(LoginResponse success, String error) {
    public static LoginResult success(LoginResponse success) {
        return new LoginResult(success, null);
    }

    public static LoginResult error(String error) {
        return new LoginResult(null, error);
    }
}
