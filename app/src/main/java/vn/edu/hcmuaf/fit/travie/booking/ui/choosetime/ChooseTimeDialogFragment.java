package vn.edu.hcmuaf.fit.travie.booking.ui.choosetime;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.tabs.TabLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;
import vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.adapter.BookingTypeAdapter;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentChooseTimeDialogBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;

/**
 * <p>A fragment that shows a list of items as a modal bottom sheet.</p>
 * <p>You can show this modal bottom sheet from your activity like this:</p>
 * <pre>
 *     ChooseTimeListDialogFragment.newInstance(30).show(getSupportFragmentManager(), "dialog");
 * </pre>
 */
public class ChooseTimeDialogFragment extends BottomSheetDialogFragment {
    private FragmentChooseTimeDialogBinding binding;

    private static final String ARG_HOTEL = "hotel";

    private Hotel hotel;

    public static ChooseTimeDialogFragment newInstance(Hotel hotel) {
        final ChooseTimeDialogFragment fragment = new ChooseTimeDialogFragment();
        final Bundle args = new Bundle();
        args.putParcelable(ARG_HOTEL, hotel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hotel = getArguments().getParcelable(ARG_HOTEL, Hotel.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentChooseTimeDialogBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initTabLayout();
        BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from((FrameLayout) view.getParent());
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        behavior.setSkipCollapsed(true);
    }

    private void initTabLayout() {
        BookingTypeAdapter adapter = new BookingTypeAdapter(requireActivity(), hotel.getBookingTypes());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setUserInputEnabled(false);
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

        BookingRequest bookingRequest = BookingRequest.getInstance();
        if (bookingRequest.getBookingType() != null) {
            int position = hotel.getBookingTypes().indexOf(bookingRequest.getBookingType());
            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
        } else {
            binding.tabLayout.selectTab(binding.tabLayout.getTabAt(0));
        }
//        TabLayout.Tab tab = binding.tabLayout.getTabAt(1);
//        if (tab != null) {
//            tab.select();
//        }
    }
}