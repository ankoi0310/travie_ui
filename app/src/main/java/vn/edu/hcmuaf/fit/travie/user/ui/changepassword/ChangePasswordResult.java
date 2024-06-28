package vn.edu.hcmuaf.fit.travie.user.ui.changepassword;

import androidx.annotation.Nullable;

import lombok.Getter;

@Getter
public class ChangePasswordResult {
    @Nullable
    private String success;

    @Nullable
    private String error;

    ChangePasswordResult(@Nullable String message, boolean success) {
        if (success) {
            this.success = message;
        } else {
            this.error = message;
        }
    }
}
