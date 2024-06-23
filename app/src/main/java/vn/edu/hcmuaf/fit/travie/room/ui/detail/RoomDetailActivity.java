package vn.edu.hcmuaf.fit.travie.room.ui.detail;

import android.os.Bundle;

import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityRoomDetailBinding;

public class RoomDetailActivity extends BaseActivity {
    ActivityRoomDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRoomDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}