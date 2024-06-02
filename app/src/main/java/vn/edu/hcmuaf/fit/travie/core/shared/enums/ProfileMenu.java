package vn.edu.hcmuaf.fit.travie.core.shared.enums;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.user.ui.view.ProfileDetailActivity;

@Getter
public enum ProfileMenu {
    PROFILE_DETAIL("Profile Detail", R.drawable.ic_profile, R.color.lavender_100, ProfileDetailActivity.class),
    CHANGE_PASSWORD("Change Password", R.drawable.ic_password_check, R.color.info_100, ProfileDetailActivity.class),
    PAYMENT("Payment", R.drawable.ic_wallet, R.color.success_20, ProfileDetailActivity.class),
    HELP("Help", R.drawable.ic_message_question, R.color.warning_100, ProfileDetailActivity.class),
    LOGOUT("Logout", R.drawable.ic_logout, R.color.error_100, ProfileDetailActivity.class);

    private final String title;
    private final int icon;
    private final int cardBackgroundColor;
    private final Class<? extends Activity> activity;

    ProfileMenu(String title, int icon, int cardBackgroundColor, Class<? extends Activity> activity) {
        this.title = title;
        this.icon = icon;
        this.cardBackgroundColor = cardBackgroundColor;
        this.activity = activity;
    }

    public static List<ProfileMenu> getProfileMenuItems() {
        return new ArrayList<ProfileMenu>() {{
            add(PROFILE_DETAIL);
            add(CHANGE_PASSWORD);
            add(PAYMENT);
            add(HELP);
            add(LOGOUT);
        }};
    }

}
