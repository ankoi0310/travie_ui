package vn.edu.hcmuaf.fit.travie.core.shared.enums;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import vn.edu.hcmuaf.fit.travie.R;

@Getter
public enum ProfileMenu {
    PROFILE_DETAIL("Profile Detail", R.drawable.user, R.color.lavender_100),
    CHANGE_PASSWORD("Change Password", R.drawable.password_check, R.color.info_100),
    PAYMENT("Payment", R.drawable.wallet, R.color.success_20),
    HELP("Help", R.drawable.message_question, R.color.warning_100),
    LOGOUT("Logout", R.drawable.logout, R.color.error_100);

    private final String title;
    private final int icon;
    private final int cardBackgroundColor;

    ProfileMenu(String title, int icon, int cardBackgroundColor) {
        this.title = title;
        this.icon = icon;
        this.cardBackgroundColor = cardBackgroundColor;
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
