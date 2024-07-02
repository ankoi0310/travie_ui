package vn.edu.hcmuaf.fit.travie.auth.ui.getstarted;

import static vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant.FACEBOOK_PERMISSIONS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.facebook.login.LoginManager;

import vn.edu.hcmuaf.fit.travie.auth.ui.login.LoginActivity;
import vn.edu.hcmuaf.fit.travie.auth.ui.register.RegisterActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.MainActivity;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityGetStartedBinding;
import vn.edu.hcmuaf.fit.travie.welcome.WelcomeActivity;

public class GetStartedActivity extends BaseActivity {
    ActivityGetStartedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGetStartedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationOnClickListener(v -> {
            // Check if this activity is the last activity in the stack
            if (isTaskRoot()) {
                // If it is, set WelcomeActivity as the root activity
                Intent intent = new Intent(this, WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            } else {
                // If it is not, navigate to the previous activity
                finish();
            }
        });

        binding.login.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        binding.guest.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        binding.register.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        });

        binding.btnFacebook.setOnClickListener(v -> LoginManager.getInstance().logInWithReadPermissions(this, FACEBOOK_PERMISSIONS));
    }
}