package vn.edu.hcmuaf.fit.travie.core.service;

import static vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant.PREFS_ACCESS_TOKEN_NAME;
import static vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant.PREFS_REFRESH_TOKEN_NAME;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class AuthManager {
    private final SharedPreferences prefs;

    public AuthManager(Context context) {
        String masterKey = initializeMasterKey();
        this.prefs = createEncryptedSharedPreferences(context, masterKey);
    }

    private SharedPreferences createEncryptedSharedPreferences(Context context, String masterKey) {
        try {
            return EncryptedSharedPreferences.create(
                    "secret_shared_prefs",
                    masterKey,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
        } catch (GeneralSecurityException | IOException e) {
            Log.e("TokenManager", "Error creating EncryptedSharedPreferences", e);
            throw new RuntimeException("Error creating EncryptedSharedPreferences", e);
        }
    }

    private String initializeMasterKey() {
        try {
            return MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        } catch (GeneralSecurityException | IOException e) {
            Log.e("TokenManager", "Error initializing MasterKey", e);
            throw new RuntimeException("Error initializing MasterKey", e);
        }
    }

    public void cacheFirstTime() {
        prefs.edit().putBoolean("first_time", false).apply();
    }

    public boolean isFirstTime() {
        return prefs.getBoolean("first_time", true);
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

    // Save username and password for auto login
    public void saveLoginInfo(String username, String password) {
        prefs.edit().putBoolean("remember_me", true).apply();
        prefs.edit().putString("username", username).apply();
        prefs.edit().putString("password", password).apply();
    }

    public void clearLoginInfo() {
        prefs.edit().remove("remember_me").apply();
        prefs.edit().remove("username").apply();
        prefs.edit().remove("password").apply();
    }

    public boolean isRememberMe() {
        return prefs.getBoolean("remember_me", false);
    }

    public String getUsername() {
        return prefs.getString("username", null);
    }

    public String getPassword() {
        return prefs.getString("password", null);
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
