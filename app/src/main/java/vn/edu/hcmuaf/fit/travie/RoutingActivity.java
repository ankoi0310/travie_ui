package vn.edu.hcmuaf.fit.travie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.splashscreen.SplashScreen;

import vn.edu.hcmuaf.fit.travie.core.service.AuthManager;
import vn.edu.hcmuaf.fit.travie.welcome.WelcomeActivity;

public class RoutingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        splashScreen.setKeepOnScreenCondition(() -> true);
        handleNavigation();
        finish();
    }

    private void handleNavigation() {
        AuthManager authManager = new AuthManager(this);
//        Intent intent = new Intent(this, tokenManager.isLoggedIn() ? MainActivity.class : WelcomeActivity.class);
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }
}