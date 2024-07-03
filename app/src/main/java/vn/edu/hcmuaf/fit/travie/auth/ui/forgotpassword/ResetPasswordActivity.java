package vn.edu.hcmuaf.fit.travie.auth.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import vn.edu.hcmuaf.fit.travie.auth.ui.login.LoginActivity;
import vn.edu.hcmuaf.fit.travie.auth.viewmodel.AuthViewModel;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AnimationUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityResetPasswordBinding;

public class ResetPasswordActivity extends AppCompatActivity {
    private final MutableLiveData<String> newPassword = new MutableLiveData<>();
    private final MutableLiveData<String> confirmPassword = new MutableLiveData<>();

    private ActivityResetPasswordBinding binding;

    AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        handleResult();

        binding.passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                newPassword.setValue(s.toString());
            }
        });

        binding.confirmPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // check match password
                if (!s.toString().equals(newPassword.getValue())) {
                    binding.confirmPasswordEt.setError("Mật khẩu không khớp");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                confirmPassword.setValue(s.toString());
            }
        });

        binding.submitBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(newPassword.getValue()) || TextUtils.isEmpty(confirmPassword.getValue())) {
                Toast.makeText(this, "Vui lòng nhập mật khẩu mới và xác nhận mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            AnimationUtil.animateView(binding.loadingView.getRoot(), View.VISIBLE, 0.4f, 200);
            String email = getIntent().getStringExtra("email");
            String newPasswordValue = newPassword.getValue();
            authViewModel.resetPassword(email, newPasswordValue);
        });
    }

    private void handleResult() {
        authViewModel.getResetPasswordResponse().observe(this, response -> {
            if (response == null) {
                Toast.makeText(this, "Không thể đặt lại mật khẩu, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            } else if (!response.isSuccess()) {
                Toast.makeText(this, "Không thể đặt lại mật khẩu: " + response.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Đặt lại mật khẩu thành công. Vui lòng đăng nhập", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                finish();
            }

            if (binding.loadingView.getRoot().getVisibility() == View.VISIBLE) {
                AnimationUtil.animateView(binding.loadingView.getRoot(), View.GONE, 0, 200);
            }
        });
    }
}
