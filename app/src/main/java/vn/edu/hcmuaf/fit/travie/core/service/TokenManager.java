package vn.edu.hcmuaf.fit.travie.core.service;

import static vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant.PREFS_ACCESS_TOKEN_NAME;
import static vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant.PREFS_REFRESH_TOKEN_NAME;

import android.content.Context;
import android.content.SharedPreferences;

import vn.edu.hcmuaf.fit.travie.R;

public class TokenManager {
    private final SharedPreferences prefs;

    public TokenManager(Context context) {
        this.prefs = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public void saveAccessToken(String accessToken) {
        prefs.edit().putString(PREFS_ACCESS_TOKEN_NAME, accessToken).apply();
    }

    public void saveRefreshToken(String refreshToken) {
        prefs.edit().putString(PREFS_REFRESH_TOKEN_NAME, refreshToken).apply();
    }

    public String getAccessToken() {
        return prefs.getString(PREFS_ACCESS_TOKEN_NAME, null);
    }

    public String getRefreshToken() {
        return prefs.getString(PREFS_REFRESH_TOKEN_NAME, null);
    }

    public void clearLoggedInUser() {
        prefs.edit().remove(PREFS_ACCESS_TOKEN_NAME).apply();
        prefs.edit().remove(PREFS_REFRESH_TOKEN_NAME).apply();
    }

    public void clearAll() {
        prefs.edit().clear().apply();
    }

    public boolean isLoggedIn() {
        return getAccessToken() != null;
    }
}