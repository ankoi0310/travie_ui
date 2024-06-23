package vn.edu.hcmuaf.fit.travie.hotel.ui.hoteldetail;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Locale;

import io.noties.markwon.Markwon;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.choosetime.ChooseTimeFragment;
import vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityHotelDetailBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;
import vn.edu.hcmuaf.fit.travie.hotel.ui.HotelViewModel;
import vn.edu.hcmuaf.fit.travie.hotel.ui.HotelViewModelFactory;

public class HotelDetailActivity extends BaseActivity {
    ActivityHotelDetailBinding binding;

    Markwon markwon;
    HotelViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this, new HotelViewModelFactory(this)).get(HotelViewModel.class);

        fetchHotelDetail();

        binding.viewMap.titleTxt.setText(R.string.view_map);

        binding.appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            // change material toolbar title to hotel name when collapsed
            if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                binding.toolbar.setTitle("Hotel Name");
            } else {
                binding.toolbar.setTitle("");
            }

            float percentage = Math.abs(verticalOffset) / (float) appBarLayout.getTotalScrollRange();
            binding.toolbar.setNavigationIconTint(AppUtil.blendColors(getColor(R.color.icon),
                    getColor(R.color.white), percentage));

            for (int i = 0; i < binding.toolbar.getMenu().size(); i++) {
                MenuItem item = binding.toolbar.getMenu().getItem(i);

                if (item.getIcon() != null) {
                    item.getIcon().setTint(AppUtil.blendColors(getColor(R.color.icon),
                            getColor(R.color.white), percentage));
                }
            }
        });
    }

    private void fetchHotelDetail() {
        Intent intent = getIntent();
        long hotelId = intent.getIntExtra("hotelId", 0);
        viewModel.getHotelById(hotelId);
        viewModel.getHotel().observe(this, result -> {
            if (result.getError() != null) {
                Toast.makeText(this, result.getError(), Toast.LENGTH_SHORT).show();
            }

            if (result.getSuccess() != null) {
                Hotel hotel = result.getSuccess();
                binding.nameTxt.setText(hotel.getName());
                binding.addressTxt.setText(hotel.getAddress().getFullAddress());
                binding.averageMarkTxt1.setText(String.valueOf(hotel.getAverageMark()));
                binding.reviewCountTxt1.setText(String.valueOf(hotel.getReviews().size()));
                binding.averageMarkTxt2.setText(String.valueOf(hotel.getAverageMark()));
                binding.reviewCountTxt2.setText(String.format(Locale.getDefault(), "%d đánh giá", hotel.getReviews().size()));

                markwon = Markwon.create(this);
                markwon.setMarkdown(binding.introductionTxt, hotel.getIntroduction());

                BookingTypeAdapter bookingTypeAdapter = new BookingTypeAdapter(hotel.getBookingTypes());
                binding.bookingTypeRv.setLayoutManager(new LinearLayoutManager(this));
                binding.bookingTypeRv.setAdapter(bookingTypeAdapter);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.chooseTimeFragment, ChooseTimeFragment.newInstance(hotel.getBookingTypes()))
                        .commit();
            }
        });

        markwon.setMarkdown(binding.cancellationPolicyTxt, AppConstant.cancellationPolicyText);
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