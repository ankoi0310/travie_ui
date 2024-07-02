package vn.edu.hcmuaf.fit.travie.welcome;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateInterpolator;

import java.time.Duration;
import java.time.Instant;

import vn.edu.hcmuaf.fit.travie.auth.ui.getstarted.GetStartedActivity;
import vn.edu.hcmuaf.fit.travie.auth.ui.login.LoginActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.MainActivity;
import vn.edu.hcmuaf.fit.travie.core.service.AuthManager;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityWelcomeBinding;

public class WelcomeActivity extends BaseActivity {
    ActivityWelcomeBinding binding;

    private final BroadcastReceiver logoutReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Navigate to the login screen
            Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AuthManager authManager = new AuthManager(this);
        if (authManager.isFirstTime()) {
            authManager.cacheFirstTime();
        } else {
            if (authManager.isLoggedIn()) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, GetStartedActivity.class);
                startActivity(intent);
                finish();
            }
        }

        binding.btnGetStarted.setOnClickListener(v -> {
            Intent intent = new Intent(WelcomeActivity.this, GetStartedActivity.class);
            startActivity(intent);
        });

        getSplashScreen().setOnExitAnimationListener(splashScreenView -> {
            // Get the duration of the animated vector drawable.
            Duration animationDuration = splashScreenView.getIconAnimationDuration();
            // Get the start time of the animation.
            Instant animationStart = splashScreenView.getIconAnimationStart();
            // Calculate the remaining duration of the animation.
            long remainingDuration;
            if (animationDuration != null && animationStart != null) {
                remainingDuration = animationDuration.minus(Duration.between(animationStart, Instant.now())).toMillis();
                remainingDuration = Math.max(remainingDuration, 0L);
            } else {
                remainingDuration = 0L;
            }

            final ObjectAnimator slideUp = ObjectAnimator.ofFloat(splashScreenView, View.TRANSLATION_Y, 0f, -splashScreenView.getHeight());
            slideUp.setInterpolator(new AnticipateInterpolator());
            slideUp.setDuration(remainingDuration);

            // Call SplashScreenView.remove at the end of your custom animation.
            slideUp.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    splashScreenView.remove();
                }
            });

            // Run your animation.
            slideUp.start();
        });

        // Register the broadcast receiver
        IntentFilter filter = new IntentFilter();
        filter.addAction("vn.edu.hcmuaf.fit.travie.ACTION_LOGOUT");
        registerReceiver(logoutReceiver, filter, Context.RECEIVER_NOT_EXPORTED);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the broadcast receiver
        unregisterReceiver(logoutReceiver);
    }
}