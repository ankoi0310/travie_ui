package vn.edu.hcmuaf.fit.travie.hotel.ui.hoteldetail;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;

import java.util.Locale;

import io.noties.markwon.Markwon;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;
import vn.edu.hcmuaf.fit.travie.booking.data.service.ChooseTimeByHourHandler;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.choosetime.ChooseTimeFragment;
import vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AnimationUtil;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityHotelDetailBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;
import vn.edu.hcmuaf.fit.travie.hotel.ui.HotelViewModel;

public class HotelDetailActivity extends BaseActivity {
    ActivityHotelDetailBinding binding;

    Markwon markwon;
    HotelViewModel hotelViewModel;

    Hotel hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHotelDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets stautusBars = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            // unit of stautusBars.top is px, so we need to convert it to dp
            int statusBarHeight = stautusBars.top / (getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
            binding.toolbar.setPadding(0, statusBarHeight + binding.toolbar.getPaddingBottom(), 0,  binding.toolbar.getPaddingBottom());
            binding.toolbar.setTitleMarginTop(statusBarHeight - 4);
            v.setPadding(0, 0, 0, stautusBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        binding.rootLayout.setVisibility(View.GONE);
        AnimationUtil.animateView(binding.loadingView.getRoot(), View.VISIBLE, 0.4f, 200);

        markwon = Markwon.create(this);
        hotelViewModel = new ViewModelProvider(this).get(HotelViewModel.class);

        fetchHotelDetail();

        binding.viewMap.titleTxt.setText(R.string.view_map);
        binding.viewMap.titleTxt.setOnClickListener(v -> {
            // Open Google Map
            String name = hotel.getName();
            String address = hotel.getAddress().getFullAddress();
            double lat = hotel.getAddress().getLatitude();
            double lng = hotel.getAddress().getLongitude();
            Intent intent = new Intent(Intent.ACTION_VIEW, AppUtil.getGoogleMapUri(address, lat, lng));
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);
        });

        binding.toolbar.setNavigationOnClickListener(v -> finish());
        binding.appbar.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            // change material toolbar title to hotel name when collapsed
            if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                binding.toolbar.setTitle(hotel.getName());
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ChooseTimeByHourHandler.destroyInstance();
        BookingRequest.destroyInstance();
    }

    private void initChooseTimeFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.chooseTimeFragment, ChooseTimeFragment.newInstance(hotel))
                .commit();
    }

    private void fetchHotelDetail() {
        Intent intent = getIntent();
        long hotelId = intent.getLongExtra("hotelId", 0);
        hotelViewModel.getHotelById(hotelId);
        hotelViewModel.getHotel().observe(this, result -> {
            if (result.getError() != null) {
                Toast.makeText(this, result.getError(), Toast.LENGTH_SHORT).show();
                finish();
            }

            if (result.getSuccess() != null) {
                hotel = result.getSuccess();

                Glide.with(this)
                        .load(hotel.getImages().get(0))
                        .centerCrop()
                        .placeholder(R.drawable.hotel_demo)
                        .into(binding.hotelImg);

                binding.nameTxt.setText(hotel.getName());
                binding.addressTxt.setText(hotel.getAddress().getFullAddress());
                binding.averageMarkTxt1.setText(String.valueOf(hotel.getAverageMark()));
                binding.reviewCountTxt1.setText(String.format(Locale.getDefault(), "(%d)", hotel.getReviews().size()));
                binding.averageMarkTxt2.setText(String.valueOf(hotel.getAverageMark()));
                binding.reviewCountTxt2.setText(String.format(Locale.getDefault(), "%d đánh giá", hotel.getReviews().size()));

                markwon.setMarkdown(binding.introductionTxt, hotel.getIntroduction() == null ? "" : hotel.getIntroduction());

                BookingTypeAdapter bookingTypeAdapter = new BookingTypeAdapter(hotel.getBookingTypes());
                binding.bookingTypeRv.setLayoutManager(new LinearLayoutManager(this));
                binding.bookingTypeRv.setAdapter(bookingTypeAdapter);

                initChooseTimeFragment();
            }

            if (binding.loadingView.getRoot().getVisibility() == View.VISIBLE) {
                AnimationUtil.animateView(binding.loadingView.getRoot(), View.GONE, 0, 200);
                binding.rootLayout.setVisibility(View.VISIBLE);
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