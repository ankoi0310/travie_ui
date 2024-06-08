package vn.edu.hcmuaf.fit.travie.auth.activity.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.MyApplication;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.auth.model.LoginRequest;
import vn.edu.hcmuaf.fit.travie.auth.model.LoginResponse;
import vn.edu.hcmuaf.fit.travie.auth.service.AuthService;
import vn.edu.hcmuaf.fit.travie.core.common.ui.MainActivity;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.service.TokenManager;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    private AuthService authService;

    private TokenManager tokenManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ((MyApplication) getApplication()).getApplicationComponent().inject(this);
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tokenManager = new TokenManager(this);

        authService = RetrofitService.createService(this, AuthService.class);

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        binding.username.addTextChangedListener(afterTextChangedListener);
        binding.password.addTextChangedListener(afterTextChangedListener);
        binding.password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.loading.setVisibility(View.VISIBLE);
                String username = binding.username.getText().toString();
                String password = binding.password.getText().toString();
                handleLogin(new LoginRequest(username, password));
            }
            return false;
        });

        binding.login.setOnClickListener(v -> {
            binding.loading.setVisibility(View.VISIBLE);
            String username = binding.username.getText().toString();
            String password = binding.password.getText().toString();
            handleLogin(new LoginRequest(username, password));
        });
    }

    private void handleLogin(LoginRequest request) {
        Call<HttpResponse<LoginResponse>> call = authService.login(request);
        call.enqueue(new Callback<HttpResponse<LoginResponse>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<LoginResponse>> call, @NonNull Response<HttpResponse<LoginResponse>> response) {
                if (!response.isSuccessful()) {
                    showLoginFailed(R.string.login_failed);
                    return;
                }

                HttpResponse<LoginResponse> httpResponse = response.body();
                if (httpResponse == null || !httpResponse.isSuccess() || httpResponse.getData() == null) {
                    showLoginFailed(R.string.login_failed);
                    return;
                }

                binding.loading.setVisibility(View.GONE);
                tokenManager.saveAccessToken(httpResponse.getData().getAccessToken());
                tokenManager.saveRefreshToken(httpResponse.getData().getRefreshToken());
                updateUiWithUser(httpResponse.getData());
                navigateToHome();
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<LoginResponse>> call, @NonNull Throwable t) {
                showLoginFailed(R.string.login_failed);
            }
        });
    }

    private void navigateToHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void updateUiWithUser(LoginResponse model) {
        String welcome = getString(R.string.welcome) + model.getAccessToken();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}