package vn.edu.hcmuaf.fit.travie.auth.activity.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.auth.activity.login.LoginActivity;
import vn.edu.hcmuaf.fit.travie.auth.activity.verify.EnterOTPActivity;
import vn.edu.hcmuaf.fit.travie.auth.model.ForgotPasswordRequest;
import vn.edu.hcmuaf.fit.travie.auth.model.VerifyOTPRequest;
import vn.edu.hcmuaf.fit.travie.auth.service.AuthService;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;

public class ForgotPasswordActivity extends AppCompatActivity {
    private AuthService authService;
    private EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        authService = RetrofitService.createService(this, AuthService.class);
        emailEditText = findViewById(R.id.editEmail);
        String email = emailEditText.getText().toString().trim();

        Button submitButton = findViewById(R.id.buttonSubmitEmail);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                    startActivity(new Intent(ForgotPasswordActivity.this, EnterOTPActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<String>> call, @NonNull Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this, "Đã xảy ra lỗi, vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}