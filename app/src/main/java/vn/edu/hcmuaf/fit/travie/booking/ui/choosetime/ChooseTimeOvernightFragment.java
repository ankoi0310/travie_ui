package vn.edu.hcmuaf.fit.travie.booking.ui.choosetime;

import static vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.ContinuousSelectionHelper.isInDateBetweenSelection;
import static vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.ContinuousSelectionHelper.isOutDateBetweenSelection;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.core.CalendarMonth;
import com.kizitonwose.calendar.core.DayPosition;
import com.kizitonwose.calendar.view.MonthDayBinder;
import com.kizitonwose.calendar.view.ViewContainer;

import org.apache.commons.text.WordUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

import kotlin.Unit;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;
import vn.edu.hcmuaf.fit.travie.core.common.ui.choosetime.ChooseTimeFragment;
import vn.edu.hcmuaf.fit.travie.databinding.CalendarByDayBinding;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentChooseTimeOvernightBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;

public class ChooseTimeOvernightFragment extends Fragment {
    FragmentChooseTimeOvernightBinding binding;
    BookingRequest bookingRequest = BookingRequest.getInstance();

    private static final String ARG_BOOKING_TYPE = "bookingType";
    private BookingType bookingType;

    private final LocalDate today = LocalDate.now();
    private DateSelection selection = new DateSelection(today, today.plusDays(1));
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd/MM");

    private ChooseTimeDialogFragment dialog;
    private TextView checkInTxt, checkOutTxt;
    private Button applyBtn;

    public ChooseTimeOvernightFragment() {
        // Required empty public constructor
    }

    public static ChooseTimeOvernightFragment newInstance(BookingType bookingType) {
        ChooseTimeOvernightFragment fragment = new ChooseTimeOvernightFragment();
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
        binding = FragmentChooseTimeOvernightBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
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
            checkInTxt = dialog.requireView().findViewById(R.id.check_in_txt);
            checkOutTxt = dialog.requireView().findViewById(R.id.check_out_txt);
            applyBtn = dialog.requireView().findViewById(R.id.apply_btn);
        }

        if (bookingRequest.getBookingType() != null && bookingRequest.getBookingType().equals(bookingType)) {
            selection = new DateSelection(bookingRequest.getCheckIn().toLocalDate(), bookingRequest.getCheckOut().toLocalDate());
            binding.calendarView.notifyCalendarChanged();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        bindSummaryViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        bindSummaryViews();
        bookingRequest.setBookingType(bookingType);
        applyBtn.setOnClickListener(v -> {
            if (selection.getEndDate() == null) {
                Toast.makeText(requireContext(), "Chưa chọn ngày trả phòng", Toast.LENGTH_SHORT).show();
                return;
            }

            LocalDateTime checkIn = LocalDateTime.of(selection.getStartDate(), bookingType.getStartTime());
            LocalDateTime checkOut = LocalDateTime.of(selection.getEndDate(), bookingType.getEndTime());

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
        if (selection.getStartDate() != null) {
            LocalDateTime checkIn = LocalDateTime.of(selection.getStartDate(), bookingType.getStartTime());
            checkInTxt.setText(formatter.format(checkIn));
        }

        if (selection.getEndDate() != null) {
            LocalDateTime checkOut = LocalDateTime.of(selection.getEndDate(), bookingType.getEndTime());
            checkOutTxt.setText(formatter.format(checkOut));
        }
    }

    private void configureBinders() {
        int clipLevelHalf = 5000;
        Context context = requireContext();
        Drawable rangeStartBackground = AppCompatResources.getDrawable(context, R.drawable.continuous_selected_bg_start);
        rangeStartBackground.setLevel(clipLevelHalf);

        Drawable rangeEndBackground = AppCompatResources.getDrawable(context, R.drawable.continuous_selected_bg_end);
        rangeEndBackground.setLevel(clipLevelHalf);

        Drawable rangeMiddleBackground = AppCompatResources.getDrawable(context, R.drawable.continuous_selected_bg_middle);
        Drawable singleBackground = AppCompatResources.getDrawable(context, R.drawable.single_selected_bg);
        Drawable todayBackground = AppCompatResources.getDrawable(context, R.drawable.today_bg);

        class DayViewContainer extends ViewContainer {
            CalendarDay day;
            final CalendarByDayBinding dayBinding;

            public DayViewContainer(@NonNull View view) {
                super(view);
                dayBinding = CalendarByDayBinding.bind(view);
                view.setOnClickListener(v -> {
                    if (day.getPosition() == DayPosition.MonthDate &&
                            (day.getDate().equals(today) || day.getDate().isAfter(today))) {
                        selection = new DateSelection(day.getDate(), day.getDate().plusDays(1));
                        binding.calendarView.notifyCalendarChanged();
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
                TextView textView = container.dayBinding.calendarDayTextByDay;
                View roundBgView = container.dayBinding.roundedBackgroundViewByDay;
                View continuousBgView = container.dayBinding.continuousBgViewByDay;

                textView.setText(null);
                roundBgView.setVisibility(View.INVISIBLE);
                continuousBgView.setVisibility(View.INVISIBLE);

                LocalDate startDate = selection.getStartDate();
                LocalDate endDate = selection.getEndDate();

                switch (day.getPosition()) {
                    case InDate -> {
                        if (startDate != null && endDate != null &&
                                isInDateBetweenSelection(day.getDate(), startDate, endDate)) {
                            applyBackground(continuousBgView, rangeMiddleBackground);
                        }
                    }
                    case MonthDate -> {
                        textView.setText(String.valueOf(day.getDate().getDayOfMonth()));

                        if (day.getDate().isBefore(today)) {
                            textView.setTextColor(context.getColor(R.color.example_4_grey_past));
                        } else {
                            if (startDate.equals(day.getDate()) && endDate == null) {
                                textView.setTextColor(context.getColor(R.color.white));
                                applyBackground(roundBgView, singleBackground);
                            } else if (day.getDate().equals(startDate)) {
                                textView.setTextColor(context.getColor(R.color.white));
                                applyBackground(continuousBgView, rangeStartBackground);
                                applyBackground(roundBgView, singleBackground);
                            } else if (endDate != null && day.getDate().isAfter(startDate) && day.getDate().isBefore(endDate)) {
                                textView.setTextColor(context.getColor(R.color.example_4_grey));
                                applyBackground(continuousBgView, rangeMiddleBackground);
                            } else if (day.getDate().equals(endDate)) {
                                textView.setTextColor(context.getColor(R.color.white));
                                applyBackground(continuousBgView, rangeEndBackground);
                                applyBackground(roundBgView, singleBackground);
                            } else if (today.equals(day.getDate())) {
                                textView.setTextColor(context.getColor(R.color.white));
                                applyBackground(roundBgView, todayBackground);
                            } else textView.setTextColor(context.getColor(R.color.example_4_grey));
                        }
                    }
                    case OutDate -> {
                        if (startDate != null && endDate != null &&
                                isOutDateBetweenSelection(day.getDate(), startDate, endDate)
                        ) {
                            applyBackground(continuousBgView, rangeMiddleBackground);
                        }
                    }
                }
            }

            private void applyBackground(View view, Drawable drawable) {
                view.setBackground(drawable);
                view.setVisibility(View.VISIBLE);
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
}