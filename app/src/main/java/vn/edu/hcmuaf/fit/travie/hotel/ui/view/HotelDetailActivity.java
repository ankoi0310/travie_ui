package vn.edu.hcmuaf.fit.travie.hotel.ui.view;

import android.os.Bundle;

import vn.edu.hcmuaf.fit.travie.core.common.view.BaseActivity;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityHotelDetailBinding;

public class HotelDetailActivity extends BaseActivity {
    ActivityHotelDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setOnApplyWindowInsetsListener(binding.main);
    }
}