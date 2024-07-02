package vn.edu.hcmuaf.fit.travie.core.common.ui.choosetime;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;
import vn.edu.hcmuaf.fit.travie.booking.data.service.ChooseTimeByHourHandler;
import vn.edu.hcmuaf.fit.travie.booking.ui.chooseroom.ChooseRoomActivity;
import vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.ChooseTimeDialogFragment;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.TimeUnit;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.BookingUtil;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.DateTimeUtil;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentChooseTimeBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseTimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseTimeFragment extends Fragment {
    private static final String ARG_HOTEL = "hotel";

    private Hotel hotel;

    private final DateTimeFormatter formatter = DateTimeUtil.getDateTimeFormatter("HH:mm, dd/MM");
    private final ChooseTimeByHourHandler handler = ChooseTimeByHourHandler.getInstance();

    FragmentChooseTimeBinding binding;
    BookingRequest bookingRequest = BookingRequest.getInstance();

    public ChooseTimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param hotel The hotel to book.
     * @return A new instance of fragment ChooseTimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChooseTimeFragment newInstance(Hotel hotel) {
        ChooseTimeFragment fragment = new ChooseTimeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_HOTEL, hotel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hotel = getArguments().getParcelable(ARG_HOTEL, Hotel.class);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChooseTimeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        handleBookingRequest();
        updateUI();

        binding.chooseRoomBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), ChooseRoomActivity.class);
            intent.putExtra("hotel", hotel);
            startActivity(intent);
        });

        initChooseTimeBottomSheet();
    }

    private void initChooseTimeBottomSheet() {
        binding.chooseTimeBtn.setOnClickListener(v -> {
            ChooseTimeDialogFragment chooseTimeDialogFragment = ChooseTimeDialogFragment.newInstance(hotel);
            chooseTimeDialogFragment.show(getParentFragmentManager(), "dialog");
        });
    }

    public void updateUI() {
        LocalDateTime checkIn = bookingRequest.getCheckIn();
        LocalDateTime checkOut = bookingRequest.getCheckOut();

        switch (bookingRequest.getBookingType().getUnit()) {
            case HOUR -> {
                long amount = ChronoUnit.HOURS.between(bookingRequest.getCheckIn(), bookingRequest.getCheckOut());
                binding.timeAmountTxt.setText(String.format(Locale.getDefault(), "%s %s", amount, TimeUnit.HOUR.getSuffix()));
            }
            case OVERNIGHT -> binding.timeAmountTxt.setText(String.format(Locale.getDefault(), "%s %s", 1, TimeUnit.OVERNIGHT.getSuffix()));
            case DAY -> {
                long amount = ChronoUnit.DAYS.between(bookingRequest.getCheckIn().truncatedTo(ChronoUnit.DAYS),
                        bookingRequest.getCheckOut().truncatedTo(ChronoUnit.DAYS));
                binding.timeAmountTxt.setText(String.format(Locale.getDefault(), "%s %s", amount, TimeUnit.DAY.getSuffix()));
            }
        }

        // check if both checkIn and checkOut are the same day
        if (checkIn.getDayOfYear() == checkOut.getDayOfYear()) {
            binding.checkInTxtFragment.setText(checkIn.format(DateTimeUtil.getDateTimeFormatter("HH:mm")));
        } else {
            binding.checkInTxtFragment.setText(checkIn.format(formatter));
        }

        binding.checkOutTxtFragment.setText(checkOut.format(formatter));

        if (hotel.getRooms() != null) {
            bookingRequest.setRoom(hotel.getRooms().get(0));
            BookingUtil.calculateTotalPrice(bookingRequest);
            binding.priceTxt.setText(AppUtil.formatCurrency(bookingRequest.getTotalPrice()));
        }
    }

    private void handleBookingRequest() {
        LocalDateTime currentTime = LocalDateTime.now().plusMinutes(30);
        if (currentTime.getMinute() < 30) {
            currentTime = currentTime.withMinute(30);
        } else if (currentTime.getMinute() > 30) {
            currentTime = currentTime.plusHours(1).truncatedTo(ChronoUnit.HOURS);
        }

        int hour = currentTime.getHour();

        if (hotel.getStartHourly() <= hour && hour + hotel.getFirstHours() <= hotel.getEndHourly()) {
            BookingType hourlyBookingType = hotel.getBookingTypes().stream()
                    .filter(bookingType -> bookingType.getUnit().equals(TimeUnit.HOUR))
                    .findFirst()
                    .orElseThrow();
            bookingRequest.setBookingType(hourlyBookingType);

            handler.setStartTime(currentTime.toLocalTime());
            handler.setHourAmount(hotel.getFirstHours());
            long maxHours = currentTime.toLocalTime().until(hourlyBookingType.getEndTime(), ChronoUnit.HOURS);
            handler.setMaxHours(maxHours);

            LocalDateTime checkOut = currentTime.plusHours(hotel.getFirstHours());

            bookingRequest.setCheckIn(currentTime);
            bookingRequest.setCheckOut(checkOut);
        } else {
            BookingType dailyBookingType = hotel.getBookingTypes().stream()
                    .filter(bookingType -> bookingType.getUnit().equals(TimeUnit.OVERNIGHT))
                    .findFirst()
                    .orElse(null);
            bookingRequest.setBookingType(dailyBookingType);

            LocalDateTime checkIn = currentTime;
            if (hour < hotel.getStartOvernight()) {
                checkIn = currentTime.withHour(hotel.getStartOvernight()).truncatedTo(ChronoUnit.HOURS);
            }
            LocalDateTime checkOut = checkIn.plusDays(1).withHour(hotel.getEndOvernight());

            bookingRequest.setCheckIn(checkIn);
            bookingRequest.setCheckOut(checkOut);
        }
    }
}