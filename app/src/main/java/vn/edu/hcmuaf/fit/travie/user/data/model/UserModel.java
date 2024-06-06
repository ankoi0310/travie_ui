package vn.edu.hcmuaf.fit.travie.user.data.model;

import lombok.Getter;
import lombok.Setter;

public class UserModel {
    @Getter
    @Setter
    public static class UserProfile {
        private String fullName;
        private String avatarUrl;
    }
}
