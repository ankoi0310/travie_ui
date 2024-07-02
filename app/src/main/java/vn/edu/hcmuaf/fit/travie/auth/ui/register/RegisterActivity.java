package vn.edu.hcmuaf.fit.travie.auth.ui.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import vn.edu.hcmuaf.fit.travie.auth.data.model.register.RegisterFormState;
import vn.edu.hcmuaf.fit.travie.auth.data.model.register.RegisterRequest;
import vn.edu.hcmuaf.fit.travie.auth.ui.login.LoginActivity;
import vn.edu.hcmuaf.fit.travie.auth.ui.login.social.SocialLoginFragment;
import vn.edu.hcmuaf.fit.travie.auth.viewmodel.AuthViewModel;
import vn.edu.hcmuaf.fit.travie.core.shared.constant.FormState;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AnimationUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private final RegisterFormState registerFormState = RegisterFormState.getInstance();
    private ActivityRegisterBinding binding;

    private RegisterRequest registerRequest = RegisterRequest.getInstance();

    AuthViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.toolbar.setNavigationOnClickListener(v -> {
            RegisterFormState.finalizeInstance();
            finish();
        });

        createEmailListener();
        createPasswordListener();

        binding.password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                hideKeyboard();
                handleSubmit();
                return true;
            }
            return false;
        });

        binding.register.setOnClickListener(v -> {
            hideKeyboard();
            handleSubmit();
        });

        getSupportFragmentManager().beginTransaction()
                .replace(binding.socialLoginFragment.getId(), SocialLoginFragment.newInstance())
                .commit();

        binding.login.setOnClickListener(v -> {
            RegisterFormState.finalizeInstance();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        handleCheckEmailResponse();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(binding.getRoot().getWindowToken(), 0);
    }

    private void createEmailListener() {
        binding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    registerFormState.setEmailError(FormState.EMAIL_IS_REQUIRED);
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                    registerFormState.setEmailError(FormState.EMAIL_IS_INVALID);
                    return;
                }

                registerFormState.setEmailError(null);
                registerRequest.setEmail(s.toString());
            }
        });
    }

    private void createPasswordListener() {
        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {
                    registerFormState.setPasswordError(FormState.PASSWORD_IS_REQUIRED);
                    return;
                }

                registerFormState.setPasswordError(null);
                registerRequest.setPassword(s.toString());
            }
        });
    }

    private void handleSubmit() {
        AnimationUtil.animateView(binding.loadingView.getRoot(), View.VISIBLE, 0.4f, 200);

        if (binding.email.getText() == null || binding.email.getText().length() == 0) {
            registerFormState.setEmailError(FormState.EMAIL_IS_REQUIRED);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.getText()).matches()) {
            registerFormState.setEmailError(FormState.EMAIL_IS_INVALID);
        } else registerFormState.setEmailError(null);

        if (binding.password.getText() == null || binding.password.getText().length() == 0) {
            registerFormState.setPasswordError(FormState.PASSWORD_IS_REQUIRED);
        } else registerFormState.setPasswordError(null);

        if (registerFormState.isFirstDataValid()) {
            viewModel.checkEmail(binding.email.getText().toString());
        } else {
            showErrors();
        }
    }

    private void handleCheckEmailResponse() {
        viewModel.getCheckEmailResponse().observe(this, response -> {
            if (response == null) {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
                return;
            }

            if (response.isSuccess()) {
                RegisterFormState.finalizeInstance();
                Intent intent = new Intent(this, FillProfileActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            }

            if (binding.loadingView.getRoot().getVisibility() == View.VISIBLE) {
                AnimationUtil.animateView(binding.loadingView.getRoot(), View.GONE, 0.4f, 200);
            }
        });
    }

    private void showErrors() {
        if (registerFormState.getEmailError() != null) {
            Toast.makeText(this, registerFormState.getEmailError(), Toast.LENGTH_SHORT).show();
        } else if (registerFormState.getPasswordError() != null) {
            Toast.makeText(this, registerFormState.getPasswordError(), Toast.LENGTH_SHORT).show();
        }

        if (binding.loadingView.getRoot().getVisibility() == View.VISIBLE) {
            AnimationUtil.animateView(binding.loadingView.getRoot(), View.GONE, 0.4f, 200);
        }
    }
}
