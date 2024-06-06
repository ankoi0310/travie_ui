package vn.edu.hcmuaf.fit.travie.user.ui.view;

import android.os.Bundle;

import vn.edu.hcmuaf.fit.travie.core.common.view.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.ProfileMenu;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityProfileDetailBinding;

public class ProfileDetailActivity extends BaseActivity {
    ActivityProfileDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set title in fragment custom app bar
        binding.appBar.titleTxt.setText(ProfileMenu.PROFILE_DETAIL.getTitle());
        binding.appBar.backBtn.setOnClickListener(v -> finish());
    }
}