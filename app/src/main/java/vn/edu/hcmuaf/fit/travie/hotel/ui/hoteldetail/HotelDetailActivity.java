package vn.edu.hcmuaf.fit.travie.hotel.ui.hoteldetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Locale;
import java.util.OptionalInt;

import javax.inject.Inject;

import io.noties.markwon.Markwon;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;
import vn.edu.hcmuaf.fit.travie.booking.data.service.BookingRequestHolder;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModel;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModelFactory;
import vn.edu.hcmuaf.fit.travie.booking.ui.chooseroom.ChooseRoomActivity;
import vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.ChooseTimeDialogFragment;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.choosetime.ChooseTimeFragment;
import vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AnimationUtil;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityHotelDetailBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;
import vn.edu.hcmuaf.fit.travie.hotel.ui.HotelViewModel;
import vn.edu.hcmuaf.fit.travie.hotel.ui.HotelViewModelFactory;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;

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

        binding.rootLayout.setVisibility(View.GONE);
        AnimationUtil.animateView(binding.loadingView.getRoot(), View.VISIBLE, 0.4f, 200);

        markwon = Markwon.create(this);
        hotelViewModel = new ViewModelProvider(this, new HotelViewModelFactory(this)).get(HotelViewModel.class);

        fetchHotelDetail();

        binding.viewMap.titleTxt.setText(R.string.view_map);

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

        binding.chooseRoomBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChooseRoomActivity.class);
            intent.putExtra("hotel", hotel);
            startActivity(intent);
        });

        initChooseTimeBottomSheet();
    }

    private void initChooseTimeBottomSheet() {
        binding.chooseTimeFragment.setOnClickListener(v -> {
            ChooseTimeDialogFragment chooseTimeDialogFragment = ChooseTimeDialogFragment.newInstance(hotel.getBookingTypes());
            chooseTimeDialogFragment.show(getSupportFragmentManager(), "dialog");
        });
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
        hotelViewModel.getHotel().observe(this, result -> new Handler(Looper.getMainLooper())
                .postDelayed(() -> {
                    if (result.getError() != null) {
                        Toast.makeText(this, result.getError(), Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    if (result.getSuccess() != null) {
                        hotel = result.getSuccess();
                        binding.nameTxt.setText(hotel.getName());
                        binding.addressTxt.setText(hotel.getAddress().getFullAddress());
                        binding.averageMarkTxt1.setText(String.valueOf(hotel.getAverageMark()));
                        binding.reviewCountTxt1.setText(String.format(Locale.getDefault(), "(%d)", hotel.getReviews().size()));
                        binding.averageMarkTxt2.setText(String.valueOf(hotel.getAverageMark()));
                        binding.reviewCountTxt2.setText(String.format(Locale.getDefault(), "%d đánh giá", hotel.getReviews().size()));

                        markwon.setMarkdown(binding.introductionTxt, hotel.getIntroduction());

                        BookingTypeAdapter bookingTypeAdapter = new BookingTypeAdapter(hotel.getBookingTypes());
                        binding.bookingTypeRv.setLayoutManager(new LinearLayoutManager(this));
                        binding.bookingTypeRv.setAdapter(bookingTypeAdapter);

                        if (hotel.getRooms() != null) {
                            int price = hotel.getRooms().stream().mapToInt(Room::getFirstHoursOrigin).min().orElse(0);
                            binding.priceTxt.setText(AppUtil.formatCurrency(price));
                        }

                        BookingRequest bookingRequest = new BookingRequest();
                        BookingRequestHolder.getInstance().setBookingRequest(bookingRequest);
                        initChooseTimeFragment();
                    }

                    if (binding.loadingView.getRoot().getVisibility() == View.VISIBLE) {
                        AnimationUtil.animateView(binding.loadingView.getRoot(), View.GONE, 0, 200);
                        binding.rootLayout.setVisibility(View.VISIBLE);
                    }
                }, 2000));

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