package vn.edu.hcmuaf.fit.travie.core.shared.constant;

public class AppConstant {
    public static class MenuTitle {
        public static final String HOME = "Home";
        public static final String EXPLORE = "Explore";
        public static final String HISTORY = "History";
        public static final String PROFILE = "Profile";
    }

    public static String TOKEN_PREFIX = "Bearer ";

    public static String PREFS_ACCESS_TOKEN_NAME = "ACCESS_TOKEN";
    public static String PREFS_REFRESH_TOKEN_NAME = "REFRESH_TOKEN";

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
