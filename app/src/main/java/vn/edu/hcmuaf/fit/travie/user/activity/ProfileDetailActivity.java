package vn.edu.hcmuaf.fit.travie.user.activity;

import static vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant.INTENT_USER_PROFILE;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import java.time.format.DateTimeFormatter;

import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.ProfileMenu;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.DateTimeUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityProfileDetailBinding;
import vn.edu.hcmuaf.fit.travie.user.model.UserProfile;
import vn.edu.hcmuaf.fit.travie.user.service.UserService;

public class ProfileDetailActivity extends BaseActivity {
    ActivityProfileDetailBinding binding;

    UserService userService;

    private UserProfile userProfile;

    private final DateTimeFormatter formatter = DateTimeUtil.getDateTimeFormatter("dd-MM-yyyy");

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

        binding.usernameTxt.addTextChangedListener(textWatcher);
        binding.updateProfileBtn.setOnClickListener(v -> {
            if (userProfile != null) {

            }
        });
    }

    private void setProfileDetail() {
        userProfile = getIntent().getParcelableExtra(INTENT_USER_PROFILE);

        if (userProfile != null) {
            binding.usernameTxt.setText(userProfile.getUsername());
            binding.emailTxt.setText(userProfile.getEmail());
            binding.phoneTxt.setText(userProfile.getPhone());
            binding.fullNameTxt.setText(userProfile.getFullName());
            binding.genderTxt.setText(userProfile.getGender().getValue());
            binding.birthdayTxt.setText(userProfile.getBirthday().format(formatter));
        }
    }
}