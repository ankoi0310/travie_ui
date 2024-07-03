package vn.edu.hcmuaf.fit.travie.auth.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import vn.edu.hcmuaf.fit.travie.auth.ui.otp.OTPActivity;
import vn.edu.hcmuaf.fit.travie.auth.viewmodel.AuthViewModel;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.otp.OTPType;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AnimationUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private ActivityForgotPasswordBinding binding;

    AuthViewModel authViewModel;

    boolean isEmailValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        handleResult();

        binding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                email.setValue(s.toString());
            }
        });

        email.observe(this, s -> isEmailValid = s.matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$"));

        binding.submitBtn.setOnClickListener(v -> {
            if (!isEmailValid) {
                Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                return;
            }

            AnimationUtil.animateView(binding.loadingView.getRoot(), View.VISIBLE, 0.4f, 200);
            authViewModel.forgotPassword(email.getValue());
        });
    }

    private void handleResult() {
        authViewModel.getForgotPasswordResponse().observe(this, response -> {
            if (response == null) {
                Toast.makeText(this, "Something went wrong, please try again!", Toast.LENGTH_SHORT).show();
            } else if (!response.isSuccess()) {
                Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(this, OTPActivity.class);
                intent.putExtra("otpType", OTPType.RESET_PASSWORD.name());
                intent.putExtra("email", email.getValue());
                startActivity(intent);
            }

            if (binding.loadingView.getRoot().getVisibility() == View.VISIBLE) {
                AnimationUtil.animateView(binding.loadingView.getRoot(), View.GONE, 0.4f, 200);
            }
        });
    }
}
