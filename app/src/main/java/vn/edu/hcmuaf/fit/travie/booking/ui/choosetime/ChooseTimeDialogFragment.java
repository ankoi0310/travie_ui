package vn.edu.hcmuaf.fit.travie.booking.ui.choosetime;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModel;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModelFactory;
import vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.adapter.UnitTimeAdapter;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.DateTimeUtil;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentChooseTimeDialogBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ChooseTimeListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class ChooseTimeDialogFragment extends BottomSheetDialogFragment {
    private FragmentChooseTimeDialogBinding binding;

    private static final String ARG_BOOKING_TYPES = "bookingTypes";

    private ArrayList<BookingType> bookingTypes;

    public static ChooseTimeDialogFragment newInstance(ArrayList<BookingType> bookingTypes) {
        final ChooseTimeDialogFragment fragment = new ChooseTimeDialogFragment();
        final Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_BOOKING_TYPES, bookingTypes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookingTypes = getArguments().getParcelableArrayList(ARG_BOOKING_TYPES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentChooseTimeDialogBinding.inflate(inflater, container, false);

//        BottomSheetBehavior<View> behavior = BottomSheetBehavior.from((View) binding.getRoot().getParent());
//        behavior.setPeekHeight(0);
//        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        behavior.setExpandedOffset(0);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initTabLayout();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialog1 -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog1;
            FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        return dialog;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTabLayout() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(ChooseTimeByHourFragment.newInstance(bookingTypes.get(0)));
        fragments.add(ChooseTimeByNightFragment.newInstance(bookingTypes.get(1)));
        fragments.add(new ChooseTimeByDayFragment());
        UnitTimeAdapter adapter = new UnitTimeAdapter(requireActivity(), fragments);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setUserInputEnabled(false);
        binding.viewPager.setOffscreenPageLimit(3);
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
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

        binding.nestedScrollView.setOnTouchListener((v, event) -> {
            v.getParent().requestDisallowInterceptTouchEvent(true);
            if (event.getAction() == MotionEvent.ACTION_UP) {
                v.performClick();
            }
            return false;
        });

        TabLayout.Tab tab = binding.tabLayout.getTabAt(1);
        if (tab != null) {
            tab.select();
        }
    }
}