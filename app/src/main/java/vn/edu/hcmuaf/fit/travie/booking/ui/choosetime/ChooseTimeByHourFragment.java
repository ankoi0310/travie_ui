package vn.edu.hcmuaf.fit.travie.booking.ui.choosetime;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.core.CalendarMonth;
import com.kizitonwose.calendar.core.DayPosition;
import com.kizitonwose.calendar.view.MonthDayBinder;
import com.kizitonwose.calendar.view.ViewContainer;

import org.apache.commons.text.WordUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;

import kotlin.Unit;
import lombok.Getter;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;
import vn.edu.hcmuaf.fit.travie.booking.data.service.ChooseTimeByHourHandler;
import vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.adapter.HourAdapter;
import vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.adapter.TimeAdapter;
import vn.edu.hcmuaf.fit.travie.core.common.ui.SpaceItemDecoration;
import vn.edu.hcmuaf.fit.travie.core.common.ui.choosetime.ChooseTimeFragment;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.DateTimeUtil;
import vn.edu.hcmuaf.fit.travie.databinding.CalendarByHourBinding;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentChooseTimeByHourBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseTimeByHourFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@Getter
public class ChooseTimeByHourFragment extends Fragment {
    FragmentChooseTimeByHourBinding binding;
    BookingRequest bookingRequest = BookingRequest.getInstance();

    private static final String ARG_BOOKING_TYPE = "bookingType";
    private BookingType bookingType;

    private final LocalDate today = LocalDate.now();
    private LocalDate selectedDate = today;
    private final ChooseTimeByHourHandler handler = ChooseTimeByHourHandler.getInstance();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd/MM");

    private ChooseTimeDialogFragment dialog;
    private TextView checkInTxt, checkOutTxt;
    private Button applyBtn;

    private TimeAdapter timeAdapter;
    private HourAdapter hourAdapter;

    private Hotel hotel;

    public ChooseTimeByHourFragment() {
        // Required empty public constructor
    }

    public static ChooseTimeByHourFragment newInstance(BookingType bookingType) {
        ChooseTimeByHourFragment fragment = new ChooseTimeByHourFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BOOKING_TYPE, bookingType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookingType = getArguments().getParcelable(ARG_BOOKING_TYPE, BookingType.class);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChooseTimeByHourBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int childCount = binding.legendLayout.getRoot().getChildCount();
        for (int i = 0; i < childCount; i++) {
            TextView child = (TextView) binding.legendLayout.getRoot().getChildAt(i);
            child.setText(DayOfWeek.values()[i].getDisplayName(TextStyle.SHORT, Locale.getDefault()));
            child.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15f);
            child.setTextColor(requireContext().getColor(R.color.example_4_grey));
        }

        configureBinders();

        dialog = (ChooseTimeDialogFragment) requireActivity().getSupportFragmentManager().findFragmentByTag("dialog");
        if (dialog != null) {
            if (dialog.getArguments() != null) {
                hotel = dialog.getArguments().getParcelable("hotel", Hotel.class);
                createHourListUI();
            }
            checkInTxt = dialog.requireView().findViewById(R.id.check_in_txt);
            checkOutTxt = dialog.requireView().findViewById(R.id.check_out_txt);
            applyBtn = dialog.requireView().findViewById(R.id.apply_btn);
        }

        List<LocalTime> times = DateTimeUtil.generateTimeList(bookingType.getStartTime(), LocalTime.of(23, 30));
        timeAdapter = new TimeAdapter(times, hotel.getFirstHours(), bookingType);
        timeAdapter.setOnTimeClickListener((position) -> {
            LocalTime time = times.get(position);

            handler.setStartTime(time);
            long maxHours = time.until(bookingType.getEndTime(), ChronoUnit.HOURS);
            handler.setMaxHours(maxHours);

            timeAdapter.setCurrentTime(time, selectedDate.equals(today));
            hourAdapter.notifyDataSetChanged();
            bindSummaryViews();
        });
        binding.rvCheckInTime.setAdapter(timeAdapter);
        binding.rvCheckInTime.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvCheckInTime.addItemDecoration(new SpaceItemDecoration(12, RecyclerView.HORIZONTAL));

        if (bookingRequest.getBookingType() != null && bookingRequest.getBookingType().equals(bookingType)) {
            selectedDate = bookingRequest.getCheckIn().toLocalDate();
            binding.calendarView.notifyDateChanged(selectedDate);
            timeAdapter.setCurrentTime(bookingRequest.getCheckIn().toLocalTime(), bookingRequest.getCheckIn().toLocalDate().equals(today));
            hourAdapter.setCurrentHour(handler.getHourAmount());
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        bindSummaryViews();
        bookingRequest.setBookingType(bookingType);
    }

    @Override
    public void onResume() {
        super.onResume();
        bindSummaryViews();
        bookingRequest.setBookingType(bookingType);
        applyBtn.setOnClickListener(v -> {
            Log.d("ChooseTimeByHourFragment", "onViewCreated: " + handler.getStartTime());
            LocalDateTime checkIn = LocalDateTime.of(selectedDate, handler.getStartTime());
            LocalDateTime checkOut = LocalDateTime.of(selectedDate, handler.getStartTime().plusHours(handler.getHourAmount()));

            bookingRequest.setCheckIn(checkIn);
            bookingRequest.setCheckOut(checkOut);

            ChooseTimeFragment chooseTimeFragment = (ChooseTimeFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.chooseTimeFragment);
            if (chooseTimeFragment != null) {
                chooseTimeFragment.updateUI();
            }
            dialog.dismiss();
        });
    }

    public void bindSummaryViews() {
        LocalDateTime checkIn = LocalDateTime.of(selectedDate, handler.getStartTime());
        checkInTxt.setText(formatter.format(checkIn));

        LocalDateTime checkOut = LocalDateTime.of(selectedDate, handler.getStartTime().plusHours(handler.getHourAmount()));
        checkOutTxt.setText(formatter.format(checkOut));
    }

    private void configureBinders() {
        Context context = requireContext();
        class DayViewContainer extends ViewContainer {
            CalendarDay day;
            final CalendarByHourBinding dayBinding;

            public DayViewContainer(@NonNull View view) {
                super(view);
                dayBinding = CalendarByHourBinding.bind(view);
                view.setOnClickListener(v -> {
                    if (day.getPosition() == DayPosition.MonthDate) {
                        selectedDate = day.getDate();
                        binding.calendarView.notifyCalendarChanged();
                        timeAdapter.setCurrentTime(bookingType.getStartTime(), selectedDate.equals(today));
                        bindSummaryViews();
                    }
                });
            }
        }

        binding.calendarView.setDayBinder(new MonthDayBinder<DayViewContainer>() {
            @NonNull
            @Override
            public DayViewContainer create(@NonNull View view) {
                return new DayViewContainer(view);
            }

            @Override
            public void bind(@NonNull DayViewContainer container, CalendarDay day) {
                container.day = day;
                TextView textView = container.dayBinding.exOneDayText;

                textView.setText(null);

                textView.setText(String.valueOf(day.getDate().getDayOfMonth()));

                if (day.getPosition() != DayPosition.MonthDate) {
                    textView.setTextColor(context.getColor(R.color.example_4_grey_past));
                } else {
                    if (day.getDate().equals(today)) {
                        if (day.getDate().equals(selectedDate)) {
                            textView.setTextColor(context.getColor(R.color.white));
                            textView.setBackgroundResource(R.drawable.single_selected_bg);
                        } else {
                            textView.setBackgroundResource(R.drawable.today_bg);
                        }
                    } else if (day.getDate().equals(selectedDate)) {
                        textView.setTextColor(context.getColor(R.color.white));
                        textView.setBackgroundResource(R.drawable.single_selected_bg);
                    } else {
                        textView.setTextColor(context.getColor(R.color.example_4_grey));
                        textView.setBackgroundResource(0);
                    }
                }
            }
        });
        binding.calendarView.setMonthScrollListener(calendarMonth -> {
            updateUI();
            return Unit.INSTANCE;
        });
        DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;
        YearMonth currentMonth = YearMonth.now();
        binding.calendarView.setup(currentMonth, currentMonth.plusYears(1), firstDayOfWeek);
        binding.calendarView.scrollToMonth(currentMonth);
    }

    private void updateUI() {
        CalendarMonth currentMonth = binding.calendarView.findFirstVisibleMonth();
        if (currentMonth == null) return;

        YearMonth currentYearMonth = currentMonth.getYearMonth();
        binding.yearTxt.setText(String.valueOf(currentYearMonth.getYear()));
        binding.monthTxt.setText(WordUtils.capitalize(currentYearMonth.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault())));
    }

    @SuppressLint("NotifyDataSetChanged")
    private void createHourListUI() {
        List<Integer> hours = DateTimeUtil.generateHourList(hotel.getFirstHours(), 10);
        hourAdapter = new HourAdapter(hours, hotel.getFirstHours());
        hourAdapter.setOnHourClickListener((position) -> {
            int hour = hours.get(position);

            handler.setHourAmount(hour);
            long maxHours = handler.getStartTime().until(bookingType.getEndTime(), ChronoUnit.HOURS);
            handler.setMaxHours(maxHours);

            hourAdapter.setCurrentHour(hour);
            timeAdapter.notifyDataSetChanged();
            bindSummaryViews();
        });
        binding.rvHourAmount.setAdapter(hourAdapter);
        binding.rvHourAmount.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvHourAmount.addItemDecoration(new SpaceItemDecoration(12, RecyclerView.HORIZONTAL));
    }
}