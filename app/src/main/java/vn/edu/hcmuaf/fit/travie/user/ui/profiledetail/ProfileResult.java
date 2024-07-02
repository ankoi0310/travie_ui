package vn.edu.hcmuaf.fit.travie.user.ui.profiledetail;

import lombok.Getter;
import vn.edu.hcmuaf.fit.travie.user.data.model.UserProfile;

public record ProfileResult(UserProfile success, String error) {
    public static ProfileResult success(UserProfile success) {
        return new ProfileResult(success, null);
    }

    public static ProfileResult error(String error) {
        return new ProfileResult(null, error);
    }
}
