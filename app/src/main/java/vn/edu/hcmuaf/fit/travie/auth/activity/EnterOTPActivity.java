package vn.edu.hcmuaf.fit.travie.auth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.auth.service.AuthService;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.otp.OTPType;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityEnterOtpactivityBinding;

public class EnterOTPActivity extends AppCompatActivity {
    private ActivityEnterOtpactivityBinding binding;
    private AuthService authService;
    private OTPType otpType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEnterOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authService = RetrofitService.createPublicService(this, AuthService.class);

        String otpTypeString = getIntent().getStringExtra("OTP_TYPE");
        if (otpTypeString != null) {
            otpType = OTPType.valueOf(otpTypeString);
        }

        setupOtpEditTextListeners();
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
                            String otp = "";
                            for (EditText editText : editTexts) {
                                otp += editText.getText().toString();
                            }
                            verify(otp);
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }
    }

    private void verify(String code) {
        Call<HttpResponse<String>> call = authService.verify(code);
        call.enqueue(new Callback<HttpResponse<String>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<String>> call, @NonNull Response<HttpResponse<String>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    if (OTPType.VERIFY_EMAIL.equals(otpType)) {
                        Toast.makeText(EnterOTPActivity.this, "Xác minh thành công, Hãy đăng nhập!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EnterOTPActivity.this, LoginActivity.class));
                        finish();
                    } else if (OTPType.RESET_PASSWORD.equals(otpType)) {
                        Toast.makeText(EnterOTPActivity.this, "Xác minh thành công, Hãy tạo lại mật khẩu!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EnterOTPActivity.this, ResetPasswordActivity.class));
                        finish();
                    }
                } else {
                    if (response.body() != null) {
                        Toast.makeText(EnterOTPActivity.this, "Xác minh thất bại: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EnterOTPActivity.this, "Xác minh thất bại, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<String>> call, @NonNull Throwable t) {
                // Xử lý lỗi khi gọi API
                Toast.makeText(EnterOTPActivity.this, "Đã xảy ra lỗi, vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
