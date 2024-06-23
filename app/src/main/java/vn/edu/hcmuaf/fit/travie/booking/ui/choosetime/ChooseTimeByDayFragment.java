package vn.edu.hcmuaf.fit.travie.booking.ui.choosetime;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kizitonwose.calendar.core.CalendarDay;
import com.kizitonwose.calendar.core.CalendarMonth;
import com.kizitonwose.calendar.core.DayPosition;
import com.kizitonwose.calendar.core.OutDateStyle;
import com.kizitonwose.calendar.view.CalendarView;
import com.kizitonwose.calendar.view.MonthDayBinder;
import com.kizitonwose.calendar.view.MonthHeaderFooterBinder;
import com.kizitonwose.calendar.view.ViewContainer;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.databinding.CalendarDayLayoutBinding;
import vn.edu.hcmuaf.fit.travie.databinding.CalendarHeaderLayoutBinding;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentChooseTimeByDayBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseTimeByDayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseTimeByDayFragment extends Fragment {
    FragmentChooseTimeByDayBinding binding;
    private LocalDate selectedDate;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ChooseTimeByDayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChooseTimeByDayFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChooseTimeByDayFragment newInstance(String param1, String param2) {
        ChooseTimeByDayFragment fragment = new ChooseTimeByDayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChooseTimeByDayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        YearMonth startMonth = YearMonth.now();
        YearMonth endMonth = startMonth.plusMonths(12);
        DayOfWeek firstDayOfWeek = DayOfWeek.MONDAY;
        binding.calendarView.setup(startMonth, endMonth, firstDayOfWeek);
        binding.calendarView.setOutDateStyle(OutDateStyle.EndOfRow);

        binding.calendarView.setDayBinder(new MonthDayBinder<DayViewContainer>() {
            @NonNull
            @Override
            public DayViewContainer create(@NonNull View view) {
                return new DayViewContainer(view);
            }

            @Override
            public void bind(@NonNull DayViewContainer container, CalendarDay day) {
                container.day = day;
                container.textView.setText(String.valueOf(day.getDate().getDayOfMonth()));

                if (day.getPosition() == DayPosition.MonthDate) {
                    if (day.getDate() == selectedDate) {
                        container.textView.setTextColor(requireActivity().getColor(R.color.primary));
                        container.textView.setBackgroundResource(R.drawable.bg_lavender_rounded_8);
                    } else {
                        container.textView.setBackgroundResource(0);
                        container.textView.setTextColor(requireActivity().getColor(R.color.black));
                    }
                } else {
                    container.textView.setTextColor(requireActivity().getColor(R.color.secondary));
                }
            }
        });

        binding.calendarView.setMonthHeaderBinder(new MonthHeaderFooterBinder<MonthViewContainer>() {
            @NonNull
            @Override
            public MonthViewContainer create(@NonNull View view) {
                return new MonthViewContainer(view);
            }

            @Override
            public void bind(@NonNull MonthViewContainer container, CalendarMonth month) {
                container.textView.setText(month.getYearMonth().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()));
                container.textView.setAllCaps(true);
                container.textView.setGravity(Gravity.START);
            }
        });
    }

    public class DayViewContainer extends ViewContainer {
        TextView textView;
        CalendarDay day;

        public DayViewContainer(@NonNull View view) {
            super(view);
            textView = CalendarDayLayoutBinding.bind(view).calendarDayText;

            view.setOnClickListener(v -> {
                if (day.getPosition() == DayPosition.MonthDate) {
                    LocalDate currentSelection = selectedDate;
                    if (currentSelection == day.getDate()) {
                        selectedDate = null;
                        binding.calendarView.notifyDateChanged(currentSelection);
                    } else {
                        selectedDate = day.getDate();

                        binding.calendarView.notifyDateChanged(day.getDate());
                        if (currentSelection != null) {
                            binding.calendarView.notifyDateChanged(currentSelection);
                        }
                    }
                }
            });
        }
    }

    public static class MonthViewContainer extends ViewContainer {
        TextView textView;

        public MonthViewContainer(@NonNull View view) {
            super(view);
            textView = CalendarHeaderLayoutBinding.bind(view).calendarHeaderTextView;
        }
    }
}