package vn.edu.hcmuaf.fit.travie.hotel.ui.hoteldetail;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityHotelDetailBinding;
import vn.edu.hcmuaf.fit.travie.hotel.fragment.HotelDetailInfo;

public class HotelDetailActivity extends BaseActivity {
    ActivityHotelDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // add fragment hotel detail info
        HotelDetailInfo hotelDetailInfo = HotelDetailInfo.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.hotelDetailInfo, hotelDetailInfo)
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar_menu, menu);

        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);

            if (item.getIcon() != null) {
                item.getIcon().setTint(AppUtil.blendColors(getColor(R.color.white),
                        getColor(R.color.icon), 0));
            }
        }
        return true;
    }
}