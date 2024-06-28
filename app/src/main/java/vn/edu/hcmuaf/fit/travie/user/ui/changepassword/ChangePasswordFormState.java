package vn.edu.hcmuaf.fit.travie.user.ui.changepassword;

import androidx.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordFormState {
    @Nullable
    private Integer currentPasswordError;

    @Nullable
    private Integer newPasswordError;

    @Nullable
    private Integer confirmPasswordError;

    private boolean isDataValid;

    ChangePasswordFormState(@Nullable Integer currentPasswordError, @Nullable Integer newPasswordError, @Nullable Integer confirmPasswordError) {
        this.currentPasswordError = currentPasswordError;
        this.newPasswordError = newPasswordError;
        this.confirmPasswordError = confirmPasswordError;
        this.isDataValid = false;
    }

    ChangePasswordFormState(boolean isDataValid) {
        this.currentPasswordError = null;
        this.newPasswordError = null;
        this.confirmPasswordError = null;
        this.isDataValid = isDataValid;
    }
}
