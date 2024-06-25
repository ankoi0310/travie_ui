package vn.edu.hcmuaf.fit.travie.user.activity;

import static vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant.INTENT_USER_PROFILE;

import com.fasterxml.jackson.annotation.JsonFormat;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.auth.model.RegisterResponse;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.Gender;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.ProfileMenu;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.DateTimeUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityProfileDetailBinding;
import vn.edu.hcmuaf.fit.travie.databinding.GenderBottomSheetBinding;
import vn.edu.hcmuaf.fit.travie.user.model.UserProfile;
import vn.edu.hcmuaf.fit.travie.user.model.UserProfileRequest;
import vn.edu.hcmuaf.fit.travie.user.service.UserService;

public class ProfileDetailActivity extends BaseActivity {
    private final DateTimeFormatter formatter = DateTimeUtil.getDateTimeFormatter("dd-MM-yyyy");
    private ActivityProfileDetailBinding binding;

    private UserService userService;

    private UserProfile userProfile;

    private final Calendar calendar = Calendar.getInstance();
    private Gender currentGender = Gender.MALE;

    private GenderBottomSheetBinding genderBinding;
    private BottomSheetDialog genderDialog;
    private boolean isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userService = RetrofitService.createService(this, UserService.class);

        setProfileDetail();

        // Set title in fragment custom app bar
        binding.appBar.titleTxt.setText(ProfileMenu.PROFILE_DETAIL.getTitle());
        binding.appBar.backBtn.setOnClickListener(v -> finish());

        binding.buttonEdit.setOnClickListener(v -> toggleEdit());

        TextWatcher textWatcher = new TextWatcher() {
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

        TextView.OnEditorActionListener onEditorActionListener = (v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                // Hide keyboard
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                return true;
            }
            return false;
        };

        binding.usernameTxt.addTextChangedListener(textWatcher);
        binding.updateProfileBtn.setOnClickListener(v -> {
            // Hide keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            if (userProfile != null) {
                updateProfile();
            }
        });

        genderBinding = GenderBottomSheetBinding.inflate(getLayoutInflater());
        genderDialog = new BottomSheetDialog(this);
        initGenderDialog();
        initDatePickerDialog();
    }

    private void initGenderDialog() {
        binding.genderTxt.setOnClickListener(v -> {
            if (!isEditing) return;

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

                binding.genderTxt.setText(genderText);
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

        binding.birthdayTxt.setOnClickListener(v -> {
            if (!isEditing) return; // Only show the date picker if in edit mode

            new DatePickerDialog(this, listener, calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }


    private void updateDateInView() {
        Instant instant = calendar.toInstant();
        LocalDate birthday = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        binding.birthdayTxt.setText(birthday.format(formatter));
    }

    private void setProfileDetail() {
        userProfile = getIntent().getParcelableExtra(INTENT_USER_PROFILE);

        if (userProfile != null) {
            binding.usernameTxt.setText(userProfile.getUsername());
            binding.emailTxt.setText(userProfile.getEmail());
            binding.phoneTxt.setText(userProfile.getPhone());
            binding.fullNameTxt.setText(userProfile.getFullName());
            binding.genderTxt.setText(userProfile.getGender().getStringRes());
            binding.birthdayTxt.setText(userProfile.getBirthday().format(formatter));
        }
    }
    private void toggleEdit() {
        isEditing = !isEditing;
        setEditable(isEditing);
        if (isEditing) {
            Toast.makeText(this, "Bạn đã có thể cập nhật thông tin.", Toast.LENGTH_SHORT).show();
        }
    }
    private void setEditable(boolean editable) {
        binding.usernameTxt.setEnabled(editable);
        binding.emailTxt.setEnabled(editable);
        binding.phoneTxt.setEnabled(editable);
        binding.fullNameTxt.setEnabled(editable);
        binding.genderTxt.setEnabled(editable);
        binding.birthdayTxt.setEnabled(editable);
    }
    private void updateProfile() {
        UserProfileRequest userProfileRequest = new UserProfileRequest(
                binding.usernameTxt.getText().toString(),
                binding.emailTxt.getText().toString(),
                binding.phoneTxt.getText().toString(),
                binding.fullNameTxt.getText().toString(),
                currentGender,
                LocalDate.parse(binding.birthdayTxt.getText().toString(), formatter)
        );




        Gson gson = new Gson();
        String requestJson = gson.toJson(userProfileRequest);
        Log.d("UserProfileRequest_JSON", requestJson);

        Log.d("UserProfileRequest", "Username: " + userProfileRequest.getUsername());
        Log.d("UserProfileRequest", "Email: " + userProfileRequest.getEmail());
        Log.d("UserProfileRequest", "Phone: " + userProfileRequest.getPhone());
        Log.d("UserProfileRequest", "Full Name: " + userProfileRequest.getFullName());
        Log.d("UserProfileRequest", "Gender: " + userProfileRequest.getGender());
        Log.d("UserProfileRequest", "Birthday: " + userProfileRequest.getBirthday());

        userService.updateProfile(userProfileRequest).enqueue(new Callback<HttpResponse<UserProfile>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<UserProfile>> call,@NonNull Response<HttpResponse<UserProfile>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userProfile = response.body().getData();
                    setEditable(false);
                    Toast.makeText(ProfileDetailActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ProfileDetailActivity.this, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<UserProfile>> call,@NonNull Throwable t) {
                Toast.makeText(ProfileDetailActivity.this, "Có lỗi xảy ra: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}