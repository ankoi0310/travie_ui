package vn.edu.hcmuaf.fit.travie.user.ui;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.user.model.UserProfile;

@Getter
@RequiredArgsConstructor
public class ProfileResult {
    private final UserProfile success;
    private final String error;
}
