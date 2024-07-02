package vn.edu.hcmuaf.fit.travie.auth.ui.register;

import static vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant.DEFAULT_GENDER;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;

import vn.edu.hcmuaf.fit.travie.auth.data.model.register.RegisterFormState;
import vn.edu.hcmuaf.fit.travie.auth.data.model.register.RegisterRequest;
import vn.edu.hcmuaf.fit.travie.auth.data.model.register.RegisterResponse;
import vn.edu.hcmuaf.fit.travie.auth.ui.otp.OTPActivity;
import vn.edu.hcmuaf.fit.travie.auth.viewmodel.AuthViewModel;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.service.FileService;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.Gender;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AnimationUtil;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.DateTimeUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityFillProfileBinding;

public class FillProfileActivity extends BaseActivity {
    private final DateTimeFormatter formatter = DateTimeUtil.getDateTimeFormatter("dd-MM-yyyy");
    private final RegisterRequest registerRequest = RegisterRequest.getInstance();
    private final RegisterFormState registerFormState = RegisterFormState.getInstance();
    ActivityFillProfileBinding binding;

    AuthViewModel viewModel;
    ActivityResultLauncher<Intent> chooseImageActivity;
    File avatarFile;

    Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFillProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.toolbar.setNavigationOnClickListener(v -> {
            RegisterRequest.finalizeInstance();
            finish();
        });

        initNicknameListener();
        initPhoneListener();
        initDatePickerDialog();
        initGenderSpinner();

        binding.btnSubmit.setOnClickListener(v -> submit());
        handleRegisterResult();

        chooseImageActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK) {
                Intent data = result.getData();
                if (data != null && data.getData() != null) {
                    Uri uri = data.getData();
                    binding.avatar.setImageURI(uri);
                    avatarFile = FileService.writeToFile(getContentResolver(), getFilesDir(), uri);
                }
            }
        });
        binding.changeAvatar.setOnClickListener(v -> imageChooser());
    }

    private void initDatePickerDialog() {
        DatePickerDialog.OnDateSetListener listener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateInView();
        };

        binding.birthday.setOnClickListener(v -> new DatePickerDialog(this, listener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show());
    }

    private void updateDateInView() {
        Instant instant = calendar.toInstant();
        LocalDate birthday = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        binding.birthday.setText(birthday.format(formatter));
        registerRequest.setBirthday(LocalDate.parse(binding.birthday.getText(), formatter));
    }

    private void initNicknameListener() {
        binding.nickname.addTextChangedListener(new TextWatcher() {
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
                registerRequest.setNickname(s.toString());
            }
        });
    }

    private void initPhoneListener() {
        binding.phone.addTextChangedListener(new TextWatcher() {
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
                registerRequest.setPhone(s.toString());
            }
        });
    }

    private void initGenderSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item);
        String[] genders = Arrays.stream(Gender.values())
                .map(gender -> getString(gender.getStringRes()))
                .toArray(String[]::new);
        adapter.addAll(genders);
        binding.spinnerGender.setAdapter(adapter);
        binding.spinnerGender.setSelection(DEFAULT_GENDER);
        binding.spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                registerRequest.setGender(Gender.values()[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void imageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        chooseImageActivity.launch(intent);
    }

    private void submit() {
        AnimationUtil.animateView(binding.loadingView.getRoot(), View.VISIBLE, 0.4f, 200);

        // Cache error

        if (registerFormState.isSecondDataValid()) {
            viewModel.register(avatarFile != null ? avatarFile : null, registerRequest);
        } else {
            showErrors();
        }
    }

    private void handleRegisterResult() {
        viewModel.getRegisterResult().observe(this, registerResult -> {
            if (registerResult.error() != null) {
                Toast.makeText(this, registerResult.error(), Toast.LENGTH_SHORT).show();
            }

            if (registerResult.success() != null) {
                RegisterResponse response = registerResult.success();
                RegisterRequest.finalizeInstance();
                Intent intent = new Intent(this, OTPActivity.class);
                intent.putExtra("email", response.getEmail());
                startActivity(intent);
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