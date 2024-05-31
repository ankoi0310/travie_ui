package vn.edu.hcmuaf.fit.travie.core.utils.constant;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.activity.ProfileDetailActivity;
import vn.edu.hcmuaf.fit.travie.domain.ProfileMenuItem;

public class AppConstant {
    public static class MenuTitle {
        public static final String HOME = "Home";
        public static final String EXPLORE = "Explore";
        public static final String HISTORY = "History";
        public static final String PROFILE = "Profile";
    }

    public static List<ProfileMenuItem> getProfileMenuItems() {
        return new ArrayList<ProfileMenuItem>() {{
            add(new ProfileMenuItem("Personal Information", R.drawable.ic_profile,
                    R.color.lavender_100, ProfileDetailActivity.class));
            add(new ProfileMenuItem("Change Password", R.drawable.ic_password_check,
                    R.color.info_100, ProfileDetailActivity.class));
            add(new ProfileMenuItem("Payment", R.drawable.ic_wallet,
                    R.color.success_100, ProfileDetailActivity.class));
            add(new ProfileMenuItem("Help", R.drawable.ic_message_question,
                    R.color.warning_100, ProfileDetailActivity.class));
            add(new ProfileMenuItem("Logout", R.drawable.ic_logout,
                    R.color.error_100, ProfileDetailActivity.class));
        }};
    }
}
