package vn.edu.hcmuaf.fit.travie.auth.data.model.register;

import androidx.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterFormState {
    private @Nullable Integer emailError;
    private @Nullable Integer passwordError;
    //    private final @Nullable Integer confirmPasswordError;
    private @Nullable Integer nicknameError;
    private @Nullable Integer phoneError;
    private @Nullable Integer genderError;
    private @Nullable Integer birthdayError;

    private static RegisterFormState instance;

    private RegisterFormState() {
    }

    public static RegisterFormState getInstance() {
        if (instance == null) {
            instance = new RegisterFormState();
        }
        return instance;
    }

    public static void finalizeInstance() {
        instance = null;
    }

    public boolean isFirstDataValid() {
        return emailError == null && passwordError == null;
    }

    public boolean isSecondDataValid() {
        return nicknameError == null && phoneError == null && genderError == null && birthdayError == null;
    }
}
