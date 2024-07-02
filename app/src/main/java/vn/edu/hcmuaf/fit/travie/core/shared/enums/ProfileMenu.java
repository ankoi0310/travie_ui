package vn.edu.hcmuaf.fit.travie.core.shared.enums;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import vn.edu.hcmuaf.fit.travie.R;

@Getter
public enum ProfileMenu {
    PROFILE_DETAIL("Chi tiết hồ sơ", R.drawable.user, R.color.lavender_100),
    CHANGE_PASSWORD("Đổi mật khẩu", R.drawable.password_check, R.color.info_100),
    HELP("Trung tâm hỗ trợ", R.drawable.message_question, R.color.warning_100),
    LOGOUT("Đăng xuất", R.drawable.logout, R.color.error_100);

    private final String title;
    private final int icon;
    private final int cardBackgroundColor;

    ProfileMenu(String title, int icon, int cardBackgroundColor) {
        this.title = title;
        this.icon = icon;
        this.cardBackgroundColor = cardBackgroundColor;
    }

    public static List<ProfileMenu> getProfileMenuItems() {
        return new ArrayList<>() {{
            add(PROFILE_DETAIL);
            add(CHANGE_PASSWORD);
            add(HELP);
            add(LOGOUT);
        }};
    }

}
