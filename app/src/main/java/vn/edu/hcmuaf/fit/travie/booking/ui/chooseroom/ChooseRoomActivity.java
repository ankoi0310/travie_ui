package vn.edu.hcmuaf.fit.travie.booking.ui.chooseroom;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.tabs.TabLayout;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Locale;

import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;
import vn.edu.hcmuaf.fit.travie.booking.ui.chooseroom.adapter.ChooseRoomAdapter;
import vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.ChooseTimeDialogFragment;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.SpaceItemDecoration;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AnimationUtil;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.DateTimeUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityChooseRoomBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;
import vn.edu.hcmuaf.fit.travie.room.ui.RoomViewModel;

public class ChooseRoomActivity extends BaseActivity {
    ActivityChooseRoomBinding binding;

    private RoomViewModel roomViewModel;

    private final BookingRequest bookingRequest = BookingRequest.getInstance();
    private final DateTimeFormatter formatter = DateTimeUtil.getDateTimeFormatter("HH:mm, dd/MM");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
//            Insets stautusBars = insets.getInsets(WindowInsetsCompat.Type.statusBars());
//            // unit of stautusBars.top is px, so we need to convert it to dp
//            int statusBarHeight = stautusBars.top / (getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
//            binding.toolbar.setPadding(0, statusBarHeight + binding.toolbar.getPaddingBottom(), 0,  binding.toolbar.getPaddingBottom());
//            binding.toolbar.setTitleMarginTop(statusBarHeight - 4);
//            v.setPadding(0, 0, 0, stautusBars.bottom);
//            return WindowInsetsCompat.CONSUMED;
//        });

        roomViewModel = new ViewModelProvider(this).get(RoomViewModel.class);
        roomViewModel.getRoomListResult().observe(this, result -> {
            if (result.getError() != null) {
                Toast.makeText(this, result.getError(), Toast.LENGTH_SHORT).show();
            }

            if (result.getSuccess() != null) {
                ArrayList<Room> rooms = result.getSuccess();
                ChooseRoomAdapter adapter = new ChooseRoomAdapter(rooms);
                binding.rvRoom.setAdapter(adapter);
                binding.rvRoom.setVisibility(rooms.isEmpty() ? View.GONE : View.VISIBLE);
                binding.noRoomTxt.setVisibility(rooms.isEmpty() ? View.VISIBLE : View.GONE);
            }

            if (binding.loadingView.getRoot().getVisibility() == View.VISIBLE) {
                AnimationUtil.animateView(binding.loadingView.getRoot(), View.GONE, 0, 200);
            }
        });

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        updateUI();
        binding.timeInfoLayout.setOnClickListener(v -> showChooseTimeDialog());

        initTabLayout();

        binding.rvRoom.setLayoutManager(new LinearLayoutManager(this));
        binding.rvRoom.addItemDecoration(new SpaceItemDecoration(32));

        TabLayout.Tab tab = binding.tabLayout.getTabAt(1);
        if (tab != null) {
            tab.select();
        }
    }

    public void updateUI() {
        LocalDateTime checkIn = bookingRequest.getCheckIn();
        LocalDateTime checkOut = bookingRequest.getCheckOut();

        switch (bookingRequest.getBookingType().getUnit()) {
            case HOUR -> {
                long amount = ChronoUnit.HOURS.between(bookingRequest.getCheckIn(), bookingRequest.getCheckOut());
                binding.timeAmountTxt.setText(String.format(Locale.getDefault(), "%s %s", amount, bookingRequest.getBookingType().getUnit().getSuffix()));
            }
            case DAY, OVERNIGHT -> {
                long amount = ChronoUnit.DAYS.between(bookingRequest.getCheckIn(), bookingRequest.getCheckOut());
                binding.timeAmountTxt.setText(String.format(Locale.getDefault(), "%s %s", amount, bookingRequest.getBookingType().getUnit().getSuffix()));
            }
        }

        binding.timeUnitTxt.setText(bookingRequest.getBookingType().getUnit().getText());
        binding.checkInTxt.setText(checkIn.format(formatter));
        binding.checkOutTxt.setText(checkOut.format(formatter));
    }

    private void showChooseTimeDialog() {
        Intent intent = getIntent();
        Hotel hotel = intent.getParcelableExtra("hotel", Hotel.class);

        if (hotel != null) {
            ChooseTimeDialogFragment dialogFragment = ChooseTimeDialogFragment.newInstance(hotel);
            dialogFragment.show(getSupportFragmentManager(), "dialog");
        } else {
            Toast.makeText(this, "Hotel not found", Toast.LENGTH_SHORT).show();
        }
    }

    private void initTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                AnimationUtil.animateView(binding.loadingView.getRoot(), View.VISIBLE, 0.4f, 200);

                int position = tab.getPosition();

                Intent intent = getIntent();
                long hotelId = intent.getIntExtra("hotelId", 2);
                roomViewModel.search(hotelId, position == 1 ? true : null);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do nothing
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Do nothing
            }
        });
    }
}