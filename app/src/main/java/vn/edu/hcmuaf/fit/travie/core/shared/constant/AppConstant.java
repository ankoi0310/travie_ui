package vn.edu.hcmuaf.fit.travie.core.shared.constant;

import androidx.annotation.StringRes;

import java.util.Arrays;
import java.util.List;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.Gender;

public class AppConstant {
    public static class MenuTitle {
        public static final @StringRes int HOME = R.string.home;
        public static final @StringRes int EXPLORE = R.string.explore;
        public static final @StringRes int HISTORY = R.string.history;
        public static final @StringRes int PROFILE = R.string.profile;
    }

    public static List<String> FACEBOOK_PERMISSIONS = Arrays.asList("email", "public_profile");

    public static int DEFAULT_GENDER = Gender.PREFER_NOT_TO_SAY.ordinal();

    public static String TOKEN_PREFIX = "Bearer ";

    public static String PREFS_ACCESS_TOKEN_NAME = "ACCESS_TOKEN";
    public static String PREFS_REFRESH_TOKEN_NAME = "REFRESH_TOKEN";


    public static String INTENT_USER_PROFILE = "USER_PROFILE";
    public static final String PREFS_LOCATION_LATITUDE = "LOCATION_LATITUDE";
    public static final String PREFS_LOCATION_LONGITUDE = "LOCATION_LONGITUDE";

    public static final String HOUR_SUFFIX = " giờ";

    public static final String cancellationPolicyText = "Đặt phòng **theo giờ**: Huỷ miễn phí trước giờ nhận phòng **1 tiếng**.\n\n" +
            "Đặt phòng **qua đêm**: Huỷ miễn phí trước giờ nhận phòng **2 tiếng**.\n\n" +
            "Đặt phòng **theo ngày**: Huỷ miễn phí trước giờ nhận phòng **12 tiếng**.\n\n" +
            "Lưu ý:\n\n" +
            "- Có thể được huỷ miễn phí tong vòng **5 phút** kể từ thời điểm đặt phòng thành " +
            "công nhưng thời điểm yêu cầu huỷ không được quá giờ nhận phòng.\n" +
            "- Không cho phép huỷ phòng với phòng Flash Sale và Ưu đãi không hoàn huỷ.";
}
