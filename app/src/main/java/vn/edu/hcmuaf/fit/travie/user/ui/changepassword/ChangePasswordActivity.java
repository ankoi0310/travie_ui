package vn.edu.hcmuaf.fit.travie.user.ui.changepassword;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.CustomEditText;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityChangePasswordBinding;

public class ChangePasswordActivity extends BaseActivity {
    ActivityChangePasswordBinding binding;
    ChangePasswordViewModel viewModel;
    private CustomEditText currentEdt, newEdt, confirmEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.appBar.titleTxt.setText(R.string.change_password);

        currentEdt = binding.currentEdt;
        newEdt = binding.newEdt;
        confirmEdt = binding.confirmEdt;

        setupPasswordToggle();

        createViewModel();
        handleTextListener();

        confirmEdt.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == KeyEvent.KEYCODE_ENTER) {
                String currentPassword = currentEdt.getText() != null ? currentEdt.getText().toString() : "";
                String newPassword = newEdt.getText() != null ? newEdt.getText().toString() : "";
                viewModel.changePassword(currentPassword, newPassword);
            }
            return false;
        });

        binding.submitBtn.setOnClickListener(v -> {
            String currentPassword = currentEdt.getText() != null ? currentEdt.getText().toString() : "";
            String newPassword = newEdt.getText() != null ? newEdt.getText().toString() : "";
            viewModel.changePassword(currentPassword, newPassword);
            finish();
        });
    }

    private void setupPasswordToggle() {
        setPasswordToggleForEditTExt(currentEdt);
        setPasswordToggleForEditTExt(newEdt);
        setPasswordToggleForEditTExt(confirmEdt);
    }

    private void setPasswordToggleForEditTExt(CustomEditText editText) {
        editText.setOnTouchListener((v, event) -> {
            v.performClick();
            final int DRAWABLE_RIGHT = 2;

            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= (editText.getRight() - editText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                    int inputType = editText.getInputType();
                    if (inputType == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye, 0);
                    } else {
                        editText.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.eye_slash, 0);
                    }
                    editText.setSelection(Objects.requireNonNull(editText.getText()).length());
                    return true;
                }
            }
            return false;
        });
    }

    private void handleTextListener() {
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
                String currentPassword = currentEdt.getText() != null ? currentEdt.getText().toString() : "";
                String newPassword = newEdt.getText() != null ? newEdt.getText().toString() : "";
                String confirmPassword = confirmEdt.getText() != null ? confirmEdt.getText().toString() : "";
                viewModel.changePaswordDataChanged(currentPassword, newPassword, confirmPassword);
            }
        };

        currentEdt.addTextChangedListener(afterTextChangedListener);
        newEdt.addTextChangedListener(afterTextChangedListener);
        confirmEdt.addTextChangedListener(afterTextChangedListener);
    }

    private void createViewModel() {
        viewModel = new ViewModelProvider(this, new ChangePasswordViewModelFactory(this)).get(ChangePasswordViewModel.class);
        viewModel.getChangePasswordFormState().observe(this, state -> {
            if (state == null) {
                return;
            }

            if (state.getCurrentPasswordError() != null) {
                binding.currentEdt.setError(getString(state.getCurrentPasswordError()));
            }

            if (state.getNewPasswordError() != null) {
                binding.newEdt.setError(getString(state.getNewPasswordError()));
            }

            if (state.getConfirmPasswordError() != null) {
                binding.confirmEdt.setError(getString(state.getConfirmPasswordError()));
            }

            binding.submitBtn.setEnabled(state.isDataValid());
        });

        viewModel.getChangePasswordResult().observe(this, result -> {
            if (result == null) {
                return;
            }

            if (result.getError() != null) {
                showSnackBar(result.getError());
            }

            if (result.getSuccess() != null) {
                showSnackBar(result.getSuccess());
                finish();
            }
        });
    }

    private void showSnackBar(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }
}