package vn.edu.hcmuaf.fit.travie.core.common.ui.choosetime;

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
import vn.edu.hcmuaf.fit.travie.booking.data.service.BookingRequestHolder;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.TimeUnit;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
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

    FragmentChooseTimeBinding binding;
    BookingRequestHolder bookingRequestHolder = BookingRequestHolder.getInstance();

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

        // Get booking request
        BookingRequest bookingRequest = bookingRequestHolder.getBookingRequest();

        if (bookingRequest != null) {
            BookingRequest updatedBookingRequest = handleBookingRequest(bookingRequest);
            initUI(updatedBookingRequest);
            bookingRequestHolder.setBookingRequest(updatedBookingRequest);
        }
    }

    private void initUI(BookingRequest bookingRequest) {
        LocalDateTime checkIn = bookingRequest.getCheckIn();
        LocalDateTime checkOut = bookingRequest.getCheckOut();

        long timeAmount = DateTimeUtil.calculateDuration(checkIn, checkOut);
        String timeString = AppUtil.toTimeString(timeAmount);
        binding.hourAmountTxt.setText(String.format(Locale.getDefault(), "%s %s", timeString, bookingRequest.getBookingType().getUnit().getLabel()));

        // check if both checkIn and checkOut are the same day
        if (checkIn.getDayOfYear() == checkOut.getDayOfYear()) {
            binding.checkInTxtFragment.setText(checkIn.format(DateTimeUtil.getDateTimeFormatter("HH:mm")));
        } else {
            binding.checkInTxtFragment.setText(checkIn.format(formatter));
        }

        binding.checkOutTxtFragment.setText(checkOut.format(formatter));
    }

    private BookingRequest handleBookingRequest(BookingRequest bookingRequest) {
        // Get current time
        LocalDateTime currentTime = LocalDateTime.now().plusHours(1);
        int hour = currentTime.getHour();

        // check hour is bettween start hourly and end hourly
        if (hotel.getStartHourly() <= hour && hour + hotel.getFirstHours() <= hotel.getEndHourly()) {
            BookingType hourlyBookingType = hotel.getBookingTypes().stream()
                    .filter(bookingType -> bookingType.getUnit().equals(TimeUnit.HOUR))
                    .findFirst()
                    .orElse(null);
            bookingRequest.setBookingType(hourlyBookingType);

            LocalDateTime checkIn = currentTime.truncatedTo(ChronoUnit.HOURS);
            LocalDateTime checkOut = checkIn.plusHours(hotel.getFirstHours());

            bookingRequest.setCheckIn(checkIn);
            bookingRequest.setCheckOut(checkOut);
        } else {
            BookingType dailyBookingType = hotel.getBookingTypes().stream()
                    .filter(bookingType -> bookingType.getUnit().equals(TimeUnit.OVERNIGHT))
                    .findFirst()
                    .orElse(null);
            bookingRequest.setBookingType(dailyBookingType);

            LocalDateTime checkIn;
            if (hour < hotel.getStartOvernight()) {
                checkIn = currentTime.withHour(hotel.getStartOvernight()).truncatedTo(ChronoUnit.HOURS);
            } else {
                checkIn = currentTime.truncatedTo(ChronoUnit.HOURS);
            }
            LocalDateTime checkOut = checkIn.plusDays(1).withHour(hotel.getEndOvernight());

            bookingRequest.setCheckIn(checkIn);
            bookingRequest.setCheckOut(checkOut);
        }

        return bookingRequest;
    }
}