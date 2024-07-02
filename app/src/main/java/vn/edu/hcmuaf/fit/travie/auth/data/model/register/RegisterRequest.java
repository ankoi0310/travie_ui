package vn.edu.hcmuaf.fit.travie.auth.data.model.register;


import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.Gender;

@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String password;
    private String nickname;
    private String phone;
    private Gender gender;
    private LocalDate birthday;

    private static RegisterRequest instance;

    private RegisterRequest() {
    }

    public static RegisterRequest getInstance() {
        if (instance == null) {
            instance = new RegisterRequest();
        }
        return instance;
    }

    public static void finalizeInstance() {
        instance = null;
    }
}
