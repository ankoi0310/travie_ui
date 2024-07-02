package vn.edu.hcmuaf.fit.travie.auth.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.Objects;

import vn.edu.hcmuaf.fit.travie.auth.data.model.login.LoginRequest;
import vn.edu.hcmuaf.fit.travie.auth.data.model.login.LoginResponse;
import vn.edu.hcmuaf.fit.travie.auth.ui.forgotpassword.ForgotPasswordActivity;
import vn.edu.hcmuaf.fit.travie.auth.ui.login.social.SocialLoginFragment;
import vn.edu.hcmuaf.fit.travie.auth.ui.register.RegisterActivity;
import vn.edu.hcmuaf.fit.travie.auth.viewmodel.AuthViewModel;
import vn.edu.hcmuaf.fit.travie.core.common.ui.MainActivity;
import vn.edu.hcmuaf.fit.travie.core.service.LocalStorage;
import vn.edu.hcmuaf.fit.travie.core.service.AuthManager;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AnimationUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    AuthViewModel authViewModel;

    private AuthManager authManager;
    private LocalStorage localStorage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authManager = new AuthManager(this);
        localStorage = new LocalStorage(this);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        handleLogin();

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        binding.username.addTextChangedListener(afterTextChangedListener);
        binding.password.addTextChangedListener(afterTextChangedListener);
        binding.password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Hide keyboard
                hideKeyboard(v);

                AnimationUtil.animateView(binding.loadingView.getRoot(), View.VISIBLE, 0.4f, 200);
                String username = binding.username.getText().toString();
                String password = binding.password.getText().toString();

                authViewModel.login(new LoginRequest(username, password));
            }
            return false;
        });

        handleRememberMe();
        handleForgotPassword();

        binding.login.setOnClickListener(v -> {
            if (binding.username.getText() == null || binding.password.getText() == null) {
                Toast.makeText(this, "Vui lòng nhập thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                return;
            }

            // Hide keyboard
            hideKeyboard(v);

            AnimationUtil.animateView(binding.loadingView.getRoot(), View.VISIBLE, 0.4f, 200);
            String username = binding.username.getText().toString();
            String password = binding.password.getText().toString();
            authViewModel.login(new LoginRequest(username, password));
        });

        getSupportFragmentManager().beginTransaction()
                .replace(binding.socialLoginFragment.getId(), SocialLoginFragment.newInstance())
                .commit();

        binding.register.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void handleRememberMe() {
        boolean isRemember = authManager.isRememberMe();
        if (isRemember) {
            binding.cbRememberMe.setChecked(true);
            String username = authManager.getUsername();
            String password = authManager.getPassword();
            binding.username.setText(username);
            binding.password.setText(password);
        }

        binding.cbRememberMe.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked && binding.username.getText() != null && binding.password.getText() != null) {
                String username = binding.username.getText().toString();
                String password = binding.password.getText().toString();
                authManager.saveLoginInfo(username, password);
            } else {
                authManager.clearLoginInfo();
            }
        });
    }

    private void handleForgotPassword() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        binding.forgotPassword.setOnClickListener(v -> startActivity(intent));
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void handleLogin() {
        authViewModel.getLoginResult().observe(this, result -> {
            if (result.error() != null) {
                Toast.makeText(this, result.error(), Toast.LENGTH_SHORT).show();
            }

            if (result.success() != null) {
                LoginResponse loginResponse = result.success();

                authManager.saveAccessToken(loginResponse.getAccessToken());
                authManager.saveRefreshToken(loginResponse.getRefreshToken());

                localStorage.saveString(LocalStorage.USER_NICKNAME, loginResponse.getNickname());
                localStorage.saveString(LocalStorage.USER_PHONE, loginResponse.getPhone());
                localStorage.saveString(LocalStorage.USER_AVATAR, loginResponse.getAvatar());
                navigateToHome();
            }

            if (binding.loadingView.getRoot().getVisibility() == View.VISIBLE) {
                AnimationUtil.animateView(binding.loadingView.getRoot(), View.GONE, 0.4f, 200);
            }
        });
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}