package vn.edu.hcmuaf.fit.travie.auth.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.auth.model.ForgotPasswordRequest;
import vn.edu.hcmuaf.fit.travie.auth.service.AuthService;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.otp.OTPType;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ActivityForgotPasswordBinding binding;
    private AuthService authService;

    private OTPType otpType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authService = RetrofitService.createPublicService(this, AuthService.class);

        binding.buttonSubmitEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.editEmail.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                } else {
                    ForgotPasswordRequest request = new ForgotPasswordRequest(email);
                    forgotPassword(request);
                }
            }
        });
    }

    private void forgotPassword(ForgotPasswordRequest request) {
        Call<HttpResponse<String>> call = authService.forgotPassword(request);
        call.enqueue(new Callback<HttpResponse<String>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<String>> call, @NonNull Response<HttpResponse<String>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Xác nhận email có tồn tại.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ForgotPasswordActivity.this, EnterOTPActivity.class);
                    intent.putExtra("OTP_TYPE", OTPType.RESET_PASSWORD.name());
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Gửi mail thất bại, vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<String>> call, @NonNull Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this, "Đã xảy ra lỗi, vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
