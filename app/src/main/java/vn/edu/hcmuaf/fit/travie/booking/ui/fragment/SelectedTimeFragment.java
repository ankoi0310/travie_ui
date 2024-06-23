package vn.edu.hcmuaf.fit.travie.booking.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.DateTimeUtil;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentSelectedTimeBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectedTimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectedTimeFragment extends Fragment {
    FragmentSelectedTimeBinding binding;

    private final DateTimeFormatter formatter = DateTimeUtil.getDateTimeFormatter("HH:mm - dd/MM/yyyy");

    private static final String ARG_CHECK_IN = "checkIn";
    private static final String ARG_CHECK_OUT = "checkOut";
    private static final String ARG_BOOKING_TYPE = "bookingType";

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private BookingType bookingType;

    public SelectedTimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param checkIn Check in time.
     * @param checkOut Check out time.
     * @param bookingType Booking type.
     * @return A new instance of fragment SelectedTimeFragment.
     */
    public static SelectedTimeFragment newInstance(LocalDateTime checkIn, LocalDateTime checkOut, BookingType bookingType) {
        SelectedTimeFragment fragment = new SelectedTimeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CHECK_IN, checkIn);
        args.putSerializable(ARG_CHECK_OUT, checkOut);
        args.putParcelable(ARG_BOOKING_TYPE, bookingType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            checkIn = (LocalDateTime) getArguments().getSerializable(ARG_CHECK_IN);
            checkOut = (LocalDateTime) getArguments().getSerializable(ARG_CHECK_OUT);
            bookingType = getArguments().getParcelable(ARG_BOOKING_TYPE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectedTimeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.checkInTxt.setText(checkIn.format(formatter));
        binding.checkOutTxt.setText(checkOut.format(formatter));

        createTimeUnitUI();
    }

    private void createTimeUnitUI() {
        int amount = 1;
        int iconRes = 0;
        int backgroundRes = 0;

        switch (bookingType.getUnit()) {
            case HOUR:
                iconRes = R.drawable.clock;
                amount = checkOut.getHour() - checkIn.getHour();
                backgroundRes = R.drawable.bg_gradient_hour;
                break;
            case DAY:
                iconRes = R.drawable.sun;
                amount = checkOut.getDayOfMonth() - checkIn.getDayOfMonth();
                backgroundRes = R.drawable.bg_gradient_day;
                break;
            case OVERNIGHT:
                iconRes = R.drawable.moon;
                backgroundRes = R.drawable.bg_gradient_overnight;
                break;
        }

        // Change icon
        binding.timeIcon.setImageResource(iconRes);

        // Change amount of time with unit
        binding.timeAmountTxt.setText(String.format(Locale.getDefault(), "%d %s", amount, bookingType.getUnit().getLabel()));

        // Change background drawable
        binding.timeLayout.setBackgroundResource(backgroundRes);
    }
}