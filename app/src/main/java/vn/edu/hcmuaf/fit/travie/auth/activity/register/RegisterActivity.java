package vn.edu.hcmuaf.fit.travie.auth.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.auth.activity.login.LoginActivity;
import vn.edu.hcmuaf.fit.travie.auth.activity.verify.EnterOTPActivity;
import vn.edu.hcmuaf.fit.travie.auth.model.RegisterRequest;
import vn.edu.hcmuaf.fit.travie.auth.model.RegisterResponse;
import vn.edu.hcmuaf.fit.travie.auth.service.AuthService;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.Gender;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityRegisterBinding;
import vn.edu.hcmuaf.fit.travie.welcome.WelcomeActivity;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authService = RetrofitService.createService(this, AuthService.class);

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        binding.fullname.addTextChangedListener(afterTextChangedListener);
        binding.email.addTextChangedListener(afterTextChangedListener);
        binding.phoneNumber.addTextChangedListener(afterTextChangedListener);
        binding.password.addTextChangedListener(afterTextChangedListener);

        binding.password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                registerUser();
                return true;
            }
            return false;
        });

        binding.register.setOnClickListener(v -> registerUser());
        setupLoginClickableSpan();
    }

    private void setupLoginClickableSpan() {
        String text = "Bạn đã có tài khoản? Đăng nhập ngay";

        SpannableString spannableString = new SpannableString(text);
        String keyword = "Đăng nhập ngay";

        int startIndex = text.indexOf(keyword);
        int endIndex = startIndex + keyword.length();

        spannableString.setSpan(
                new ForegroundColorSpan(getResources().getColor(R.color.primary)),
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        };

        spannableString.setSpan(clickableSpan, startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.loginavailable.setText(spannableString);
        binding.loginavailable.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void registerUser() {
        binding.loading.setVisibility(View.VISIBLE);


        String emailValue = binding.email.getText().toString();
        String phoneNumberValue = binding.phoneNumber.getText().toString();
        String fullNameValue = binding.fullname.getText().toString();
        String passwordValue = binding.password.getText().toString();

        if (emailValue.isEmpty() || phoneNumberValue.isEmpty() ||
                fullNameValue.isEmpty() || passwordValue.isEmpty()) {
            binding.loading.setVisibility(View.GONE);
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterRequest request = new RegisterRequest(fullNameValue, emailValue, phoneNumberValue, passwordValue);
        handleRegister(request);
    }

    private void handleRegister(RegisterRequest request) {
        Call<HttpResponse<RegisterResponse>> call = authService.register(request);
        call.enqueue(new Callback<HttpResponse<RegisterResponse>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<RegisterResponse>> call, @NonNull Response<HttpResponse<RegisterResponse>> response) {
                binding.loading.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    HttpResponse<RegisterResponse> httpResponse = response.body();
                    if (httpResponse != null && httpResponse.isSuccess() && httpResponse.getData() != null) {
                        Intent intent = new Intent(RegisterActivity.this, EnterOTPActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        showRegisterFailed("Đăng ký không thành công. Vui lòng thử lại.");
                    }
                } else {
                    String errorMessage = "Đăng ký không thành công. Lỗi từ server: " + response.message();
                    showRegisterFailed(errorMessage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<RegisterResponse>> call, @NonNull Throwable t) {
                binding.loading.setVisibility(View.GONE);
                String errorMessage = getErrorMessage(t);
                showRegisterFailed(errorMessage);
            }
        });
    }

    private void showRegisterFailed(String errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_LONG).show();
    }

    private String getErrorMessage(Throwable t) {
        if (t instanceof java.net.SocketTimeoutException) {
            return "Kết nối bị hết thời gian chờ. Vui lòng kiểm tra kết nối mạng của bạn.";
        } else if (t instanceof java.net.UnknownHostException) {
            return "Không thể kết nối tới server. Vui lòng kiểm tra kết nối mạng của bạn.";
        } else {
            return "Có lỗi xảy ra. Vui lòng thử lại. Lỗi: " + t.getMessage();
        }
    }
}
