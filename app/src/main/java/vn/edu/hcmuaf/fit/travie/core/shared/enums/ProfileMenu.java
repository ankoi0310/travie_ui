package vn.edu.hcmuaf.fit.travie.core.shared.enums;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.user.activity.ProfileDetailActivity;

@Getter
public enum ProfileMenu {
    PROFILE_DETAIL("Profile Detail", R.drawable.user, R.color.lavender_100, ProfileDetailActivity.class),
    CHANGE_PASSWORD("Change Password", R.drawable.password_check, R.color.info_100, ProfileDetailActivity.class),
    PAYMENT("Payment", R.drawable.wallet, R.color.success_20, ProfileDetailActivity.class),
    HELP("Help", R.drawable.message_question, R.color.warning_100, ProfileDetailActivity.class),
    LOGOUT("Logout", R.drawable.logout, R.color.error_100, ProfileDetailActivity.class);

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
