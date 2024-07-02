package vn.edu.hcmuaf.fit.travie.auth.data.model.forgotpassword;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPasswordRequest {
    String otpCode;
    String newPassword;
}
