package vn.edu.hcmuaf.fit.travie.auth.ui.otp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import vn.edu.hcmuaf.fit.travie.auth.ui.login.LoginActivity;
import vn.edu.hcmuaf.fit.travie.auth.ui.forgotpassword.ResetPasswordActivity;
import vn.edu.hcmuaf.fit.travie.auth.viewmodel.AuthViewModel;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.otp.OTPType;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityOtpBinding;

public class OTPActivity extends AppCompatActivity {
    private ActivityOtpBinding binding;

    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupOtpEditTextListeners();

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.getVerifyResponse().observe(this, response -> {
            if (response == null) {
                Toast.makeText(this, "Xác minh thất bại, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
            } else if (!response.isSuccess()) {
                Toast.makeText(this, "Xác minh thất bại: " + response.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = getIntent();
                OTPType otpType = OTPType.fromString(intent.getStringExtra("otpType"));
                switch (otpType) {
                    case VERIFY_EMAIL -> {
                        startActivity(new Intent(OTPActivity.this, LoginActivity.class));
                        finish();
                    }
                    case VERIFY_PHONE -> {
                    }
                    case RESET_PASSWORD -> {
                        Intent resetPasswordIntent = new Intent(OTPActivity.this, ResetPasswordActivity.class);
                        resetPasswordIntent.putExtra("email", getIntent().getStringExtra("email"));
                        startActivity(resetPasswordIntent);
                        finish();
                    }
                }
            }
        });
    }

    private void setupOtpEditTextListeners() {
        EditText[] editTexts = {
                binding.editTextOTP1,
                binding.editTextOTP2,
                binding.editTextOTP3,
                binding.editTextOTP4,
                binding.editTextOTP5,
                binding.editTextOTP6
        };

        for (int i = 0; i < editTexts.length; i++) {
            final EditText editText = editTexts[i];
            final int finalI = i;

            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1) {
                        if (finalI < editTexts.length - 1) {
                            editTexts[finalI + 1].requestFocus();
                        } else {
                            // Nếu đang ở ô cuối cùng thì kiểm tra OTP
                            StringBuilder code = new StringBuilder();
                            for (EditText editText : editTexts) {
                                code.append(editText.getText().toString());
                            }
                            authViewModel.verify(code.toString());
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }
}
