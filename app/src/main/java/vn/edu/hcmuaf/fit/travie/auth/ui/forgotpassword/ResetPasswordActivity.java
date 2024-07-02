package vn.edu.hcmuaf.fit.travie.auth.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.auth.data.model.forgotpassword.ResetPasswordRequest;
import vn.edu.hcmuaf.fit.travie.auth.data.service.AuthService;
import vn.edu.hcmuaf.fit.travie.auth.ui.login.LoginActivity;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityResetPasswordBinding;

public class ResetPasswordActivity extends AppCompatActivity {
    private ActivityResetPasswordBinding binding;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authService = RetrofitService.createPublicService(AuthService.class);

        binding.buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = binding.editTextNewPassword.getText().toString().trim();
                String confirmPassword = binding.editTextConfirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(ResetPasswordActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(ResetPasswordActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    // Get the OTP code from intent or other means
                    String otpCode = getIntent().getStringExtra("OTP_CODE");
                    ResetPasswordRequest request = new ResetPasswordRequest(otpCode, newPassword);
                    resetPassword(request);
                }
            }
        });
    }

    private void resetPassword(ResetPasswordRequest request) {
        Call<HttpResponse<Void>> call = authService.resetPassword(request);
        call.enqueue(new Callback<HttpResponse<Void>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<Void>> call, @NonNull Response<HttpResponse<Void>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(ResetPasswordActivity.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(ResetPasswordActivity.this, "Failed to reset password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<Void>> call, @NonNull Throwable t) {
                Toast.makeText(ResetPasswordActivity.this, "Error occurred. Please try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
