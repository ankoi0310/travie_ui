package vn.edu.hcmuaf.fit.travie.auth.activity.register;

import static vn.edu.hcmuaf.fit.travie.R.*;

import android.app.DatePickerDialog;
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
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.MyApplication;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.auth.activity.login.LoginActivity;
import vn.edu.hcmuaf.fit.travie.auth.activity.verify.EnterOTPActivity;
import vn.edu.hcmuaf.fit.travie.auth.model.RegisterRequest;
import vn.edu.hcmuaf.fit.travie.auth.model.RegisterResponse;
import vn.edu.hcmuaf.fit.travie.auth.service.AuthService;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.service.TokenManager;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.Gender;
import vn.edu.hcmuaf.fit.travie.welcome.WelcomeActivity;

public class RegisterActivity extends AppCompatActivity {
    TextView textViewLogin;
    EditText username, email, phoneNumber, fullname, birthday, password;
    RadioButton maleRadioButton, femaleRadioButton, otherRadioButton, preferNotToSayRadioButton;

    private AuthService authService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        authService = RetrofitService.createService(this, AuthService.class);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phoneNumber = findViewById(R.id.phone_number);
        fullname = findViewById(R.id.fullname);
        birthday = findViewById(R.id.birthday);
        password = findViewById(R.id.password);

        maleRadioButton = findViewById(R.id.radio_male);
        femaleRadioButton = findViewById(R.id.radio_female);
        otherRadioButton = findViewById(R.id.radio_other);
        preferNotToSayRadioButton = findViewById(R.id.radio_prefer_not_to_say);

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

        username.addTextChangedListener(afterTextChangedListener);
        email.addTextChangedListener(afterTextChangedListener);
        phoneNumber.addTextChangedListener(afterTextChangedListener);
        fullname.addTextChangedListener(afterTextChangedListener);
        birthday.addTextChangedListener(afterTextChangedListener);
        password.addTextChangedListener(afterTextChangedListener);

        password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                registerUser();
                return true;
            }
            return false;
        });

        findViewById(R.id.register).setOnClickListener(v -> registerUser());
        textViewLogin = findViewById(R.id.loginavailable);
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

        textViewLogin.setText(spannableString);
        textViewLogin.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private void registerUser() {
        findViewById(R.id.loading).setVisibility(View.VISIBLE);

        String usernameValue = username.getText().toString();
        String emailValue = email.getText().toString();
        String phoneNumberValue = phoneNumber.getText().toString();
        String fullNameValue = fullname.getText().toString();
        Gender gender = getSelectedGender();
        String birthdayStringValue = birthday.getText().toString();
        String passwordValue = password.getText().toString();

        if (usernameValue.isEmpty() || emailValue.isEmpty() || phoneNumberValue.isEmpty() ||
                fullNameValue.isEmpty() || gender == null || birthdayStringValue.isEmpty() || passwordValue.isEmpty()) {
            findViewById(R.id.loading).setVisibility(View.GONE);
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate birthdayValue = LocalDate.parse(birthdayStringValue, formatter);

            RegisterRequest request = new RegisterRequest(usernameValue, emailValue, phoneNumberValue, fullNameValue, gender, birthdayStringValue, passwordValue);
            handleRegister(request);
        } catch (DateTimeParseException e) {
            findViewById(R.id.loading).setVisibility(View.GONE);
            Toast.makeText(this, "Invalid birthday format", Toast.LENGTH_SHORT).show();
        }
    }

    private Gender getSelectedGender() {
        if (maleRadioButton.isChecked()) {
            return Gender.MALE;
        } else if (femaleRadioButton.isChecked()) {
            return Gender.FEMALE;
        } else if (otherRadioButton.isChecked()) {
            return Gender.OTHER;
        } else if (preferNotToSayRadioButton.isChecked()) {
            return Gender.PREFER_NOT_TO_SAY;
        } else {
            return null;
        }
    }

    private void handleRegister(RegisterRequest request) {
        Call<HttpResponse<RegisterResponse>> call = authService.register(request);
        call.enqueue(new Callback<HttpResponse<RegisterResponse>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<RegisterResponse>> call, @NonNull Response<HttpResponse<RegisterResponse>> response) {
                findViewById(R.id.loading).setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    HttpResponse<RegisterResponse> httpResponse = response.body();
                    if (httpResponse != null && httpResponse.isSuccess() && httpResponse.getData() != null) {
                        Intent intent = new Intent(RegisterActivity.this, EnterOTPActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        showRegisterFailed(R.string.register_failed);
                    }
                } else {
                    // Xử lý lỗi từ server
                    showRegisterFailed(R.string.register_failed);
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<RegisterResponse>> call, @NonNull Throwable t) {
                findViewById(R.id.loading).setVisibility(View.GONE);
                showRegisterFailed(R.string.register_failed);
            }
        });
    }

    private void showRegisterFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
