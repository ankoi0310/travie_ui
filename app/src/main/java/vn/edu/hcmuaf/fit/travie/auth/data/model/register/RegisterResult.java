package vn.edu.hcmuaf.fit.travie.auth.data.model.register;

public record RegisterResult(RegisterResponse success, String error) {
    public static RegisterResult success(RegisterResponse success) {
        return new RegisterResult(success, null);
    }

    public static RegisterResult error(String error) {
        return new RegisterResult(null, error);
    }
}
