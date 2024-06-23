package vn.edu.hcmuaf.fit.travie.booking.ui.chooseroom;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModel;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModelFactory;
import vn.edu.hcmuaf.fit.travie.booking.ui.chooseroom.adapter.RoomAdapter;
import vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.ChooseTimeByDayFragment;
import vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.ChooseTimeByHourFragment;
import vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.ChooseTimeByNightFragment;
import vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.adapter.UnitTimeAdapter;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.SpaceItemDecoration;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AnimationUtil;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.DateTimeUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityChooseRoomBinding;
import vn.edu.hcmuaf.fit.travie.databinding.ChooseTimeBottomSheetBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;
import vn.edu.hcmuaf.fit.travie.room.data.service.RoomService;
import vn.edu.hcmuaf.fit.travie.room.ui.RoomViewModel;
import vn.edu.hcmuaf.fit.travie.room.ui.RoomViewModelFactory;

public class ChooseRoomActivity extends BaseActivity {
    ActivityChooseRoomBinding binding;
    ChooseTimeBottomSheetBinding timeBottomSheetBinding;
    RoomAdapter adapter;

    private RoomViewModel roomViewModel;
    private BookingViewModel bookingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        RoomService roomService = RetrofitService.createService(this, RoomService.class);
        roomViewModel = new ViewModelProvider(this, new RoomViewModelFactory(roomService)).get(RoomViewModel.class);
        roomViewModel.getRoomListResult().observe(this, rooms -> {
            new Handler(Looper.getMainLooper())
                    .postDelayed(() -> {
                        AnimationUtil.animateView(binding.loadingView.getRoot(), View.GONE, 0, 200);
                        if (rooms.getError() != null) {
                            Toast.makeText(this, rooms.getError(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (rooms.getSuccess() != null) {
                            adapter.setRooms(rooms.getSuccess());
                            binding.rvRoom.setVisibility(rooms.getSuccess().isEmpty() ? View.GONE : View.VISIBLE);
                            binding.noRoomTxt.setVisibility(rooms.getSuccess().isEmpty() ? View.VISIBLE : View.GONE);
                        }
                    }, 2000);
        });

        bookingViewModel = new ViewModelProvider(this, new BookingViewModelFactory(this)).get(BookingViewModel.class);

        initTabLayout();
        initChooseTimeBottomSheet();

        adapter = new RoomAdapter();
        binding.rvRoom.setAdapter(adapter);
        binding.rvRoom.setLayoutManager(new LinearLayoutManager(this));
        binding.rvRoom.addItemDecoration(new SpaceItemDecoration(32));
    }

    private void initChooseTimeBottomSheet() {
        timeBottomSheetBinding = ChooseTimeBottomSheetBinding.inflate(getLayoutInflater());
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(timeBottomSheetBinding.getRoot(), new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        binding.timeInfoLayout.setOnClickListener(v -> {
            FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                behavior.setSkipCollapsed(true);
                behavior.setExpandedOffset(0);
            }

            bottomSheetDialog.show();
        });

        UnitTimeAdapter adapter = new UnitTimeAdapter(this, new ArrayList<Fragment>() {{
            add(ChooseTimeByHourFragment.newInstance(BookingType.demo1()));
            add(ChooseTimeByNightFragment.newInstance(BookingType.demo3()));
            add(new ChooseTimeByDayFragment());
        }});
        timeBottomSheetBinding.viewPager.setAdapter(adapter);
        timeBottomSheetBinding.viewPager.setUserInputEnabled(false);
        timeBottomSheetBinding.viewPager.setOffscreenPageLimit(3);

        new TabLayoutMediator(timeBottomSheetBinding.tabLayout, timeBottomSheetBinding.viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(R.string.by_hour);
                    break;
                case 1:
                    tab.setText(R.string.by_night);
                    break;
                case 2:
                    tab.setText(R.string.by_day);
                    break;
            }
        }).attach();

        timeBottomSheetBinding.nestedScrollView.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            if (event.getAction() == MotionEvent.ACTION_UP) {
                v.performClick();
            }
            return false;
        });

        bookingViewModel.getChooseTimeResult().observe(this, result -> {
            DateTimeFormatter formatter = DateTimeUtil.getDateTimeFormatter("HH:mm, dd/MM");
            if (result != null) {
                timeBottomSheetBinding.checkInDateTimeTxt.setText(result.getCheckIn().format(formatter));
                timeBottomSheetBinding.checkOutDateTimeTxt.setText(result.getCheckOut().format(formatter));
            }
        });

        TabLayout.Tab tab = timeBottomSheetBinding.tabLayout.getTabAt(2);
        if (tab != null) {
            tab.select();
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

        TabLayout.Tab tab = binding.tabLayout.getTabAt(1);
        if (tab != null) {
            tab.select();
        }
    }
}