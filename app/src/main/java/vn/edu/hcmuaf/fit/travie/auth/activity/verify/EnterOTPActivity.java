package vn.edu.hcmuaf.fit.travie.auth.activity.verify;

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
import vn.edu.hcmuaf.fit.travie.auth.model.VerifyOTPRequest;
import vn.edu.hcmuaf.fit.travie.auth.service.AuthService;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;

public class EnterOTPActivity extends AppCompatActivity {

    private EditText editTextOTP;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otpactivity);

        authService = RetrofitService.createService(this, AuthService.class);

        editTextOTP = findViewById(R.id.editTextOTP);
        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = editTextOTP.getText().toString();
                VerifyOTPRequest request = new VerifyOTPRequest(otp);
                verify(request);
            }
        });
    }

    private void verify(VerifyOTPRequest request) {
        Call<HttpResponse<String>> call = authService.verify(request);
        call.enqueue(new Callback<HttpResponse<String>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<String>> call, @NonNull Response<HttpResponse<String>> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Toast.makeText(EnterOTPActivity.this, "Xác minh thành công!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EnterOTPActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(EnterOTPActivity.this, "Xác minh thất bại, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<String>> call, @NonNull Throwable t) {
                Toast.makeText(EnterOTPActivity.this, "Đã xảy ra lỗi, vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}