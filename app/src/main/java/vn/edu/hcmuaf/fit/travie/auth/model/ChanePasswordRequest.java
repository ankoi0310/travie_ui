package vn.edu.hcmuaf.fit.travie.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChanePasswordRequest {
    private String currentPassword;
    private String newPassword;
}
