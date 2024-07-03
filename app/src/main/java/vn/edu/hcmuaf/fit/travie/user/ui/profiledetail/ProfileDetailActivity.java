package vn.edu.hcmuaf.fit.travie.user.ui.profiledetail;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.service.AuthManager;
import vn.edu.hcmuaf.fit.travie.core.service.LocalStorage;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.Gender;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.DateTimeUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityProfileDetailBinding;
import vn.edu.hcmuaf.fit.travie.databinding.GenderBottomSheetBinding;
import vn.edu.hcmuaf.fit.travie.user.data.model.UserProfile;
import vn.edu.hcmuaf.fit.travie.user.data.model.UserProfileRequest;
import vn.edu.hcmuaf.fit.travie.user.ui.UserViewModel;
import vn.edu.hcmuaf.fit.travie.user.ui.UserViewModelFactory;

public class ProfileDetailActivity extends BaseActivity {
    private final DateTimeFormatter formatter = DateTimeUtil.getDateTimeFormatter("dd-MM-yyyy");
    private ActivityProfileDetailBinding binding;

    private UserViewModel userViewModel;

    private final Calendar calendar = Calendar.getInstance();
    private Gender currentGender = Gender.MALE;
    private boolean isUpdating = false;

    private GenderBottomSheetBinding genderBinding;
    private BottomSheetDialog genderDialog;

    private LocalStorage localStorage;

    private final UserProfileRequest userProfileRequest = UserProfileRequest.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        localStorage = new LocalStorage(this);

        userViewModel = new ViewModelProvider(this, new UserViewModelFactory(this)).get(UserViewModel.class);
        userViewModel.getProfile();

        handleLoadUserProfile();

        binding.nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userProfileRequest.setNickname(s.toString());
            }
        });
        binding.phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userProfileRequest.setPhone(s.toString());
            }
        });

        binding.updateBtn.setOnClickListener(v -> {
            isUpdating = true;
            // Hide keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            userProfileRequest.setNickname(binding.nickname.getText().toString());
            userProfileRequest.setEmail(binding.email.getText().toString());
            userProfileRequest.setPhone(binding.phone.getText().toString());
            userProfileRequest.setGender(currentGender);
            userProfileRequest.setBirthday(LocalDate.parse(binding.birthday.getText(), formatter));

            userViewModel.updateUserProfile(userProfileRequest);
        });

        genderBinding = GenderBottomSheetBinding.inflate(getLayoutInflater());
        genderDialog = new BottomSheetDialog(this);
        initGenderDialog();
    }

    private void initGenderDialog() {
        binding.gender.setOnClickListener(v -> {
            genderDialog.setContentView(genderBinding.getRoot());

            // Pre-select the radio button based on the current gender
            switch (currentGender) {
                case MALE:
                    genderBinding.maleBtn.setChecked(true);
                    break;
                case FEMALE:
                    genderBinding.femaleBtn.setChecked(true);
                    break;
                case OTHER:
                    genderBinding.otherBtn.setChecked(true);
                    break;
                case PREFER_NOT_TO_SAY:
                    genderBinding.preferNotToSayBtn.setChecked(true);
                    break;
            }

            genderBinding.genderGroup.setOnCheckedChangeListener((group, checkedId) -> {
                group.check(checkedId);

                int genderText;
                if (checkedId == R.id.maleBtn) {
                    genderText = R.string.male;
                } else if (checkedId == R.id.femaleBtn) {
                    genderText = R.string.female;
                } else if (checkedId == R.id.otherBtn) {
                    genderText = R.string.other;
                } else {
                    genderText = R.string.prefer_not_to_say;
                }

                binding.gender.setText(genderText);
                currentGender = Gender.fromStringRes(genderText);

                genderDialog.dismiss();
            });

            genderDialog.show();
        });
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
        userProfileRequest.setBirthday(birthday);
    }

    private void handleLoadUserProfile() {
        userViewModel.getProfileResult().observe(this, result -> {
            if (result.error() != null) {
                Toast.makeText(this, result.error(), Toast.LENGTH_SHORT).show();
            }

            if (result.success() != null) {
                UserProfile userProfile = result.success();
                updateUI(userProfile);
                initDatePickerDialog();

                if (isUpdating) {
                    Toast.makeText(this, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                    isUpdating = false;

                    localStorage.saveString(LocalStorage.USER_NICKNAME, userProfile.getNickname());
                    localStorage.saveString(LocalStorage.USER_PHONE, userProfile.getPhone());

                    finish();
                }
            }
        });
    }

    private void updateUI(UserProfile userProfile) {
        binding.nickname.setText(userProfile.getNickname());
        binding.email.setText(userProfile.getEmail());
        binding.phone.setText(userProfile.getPhone());
        binding.gender.setText(userProfile.getGender().getStringRes());
        binding.birthday.setText(userProfile.getBirthday().format(formatter));
    }

}