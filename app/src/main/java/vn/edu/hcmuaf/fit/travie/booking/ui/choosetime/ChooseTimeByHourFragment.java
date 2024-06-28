package vn.edu.hcmuaf.fit.travie.booking.ui.choosetime;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import lombok.Getter;
import vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.adapter.HourAdapter;
import vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.adapter.TimeAdapter;
import vn.edu.hcmuaf.fit.travie.core.common.ui.SpaceItemDecoration;
import vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.DateTimeUtil;
import vn.edu.hcmuaf.fit.travie.databinding.FragmentChooseTimeByHourBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseTimeByHourFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@Getter
public class ChooseTimeByHourFragment extends Fragment {
    FragmentChooseTimeByHourBinding binding;
    Calendar currentCalendar = Calendar.getInstance();
    LocalDateTime checkInTime, checkOutTime;
    int hourAmount = 1;

    private static final String ARG_BOOKING_TYPE = "bookingType";

    private BookingType bookingType;
    private TimeAdapter timeAdapter;
    private HourAdapter hourAdapter;

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
            bookingType = getArguments().getParcelable(ARG_BOOKING_TYPE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChooseTimeByHourBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpTime();

        List<String> timeList = initTimeList();

        timeAdapter = new TimeAdapter(timeList);
        timeAdapter.setOnTimeClickListener((position) -> {
            timeAdapter.setCurrentTime(timeList.get(position));

            LocalTime time = LocalTime.parse(timeList.get(position));
            checkInTime = LocalDateTime.of(currentCalendar.get(Calendar.YEAR), currentCalendar.get(Calendar.MONTH), currentCalendar.get(Calendar.DAY_OF_MONTH), time.getHour(), time.getMinute());
            checkOutTime = checkInTime.plusHours(hourAmount);

        });
        binding.rvCheckInTime.setAdapter(timeAdapter);
        binding.rvCheckInTime.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvCheckInTime.addItemDecoration(new SpaceItemDecoration(12, RecyclerView.HORIZONTAL));

        createHourListUI();

        binding.calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            currentCalendar.set(year, month, dayOfMonth);
            List<String> newTimeList = initTimeList();
            timeAdapter.setTimes(newTimeList);
        });
    }

    private void setUpTime() {
        LocalTime currentTime = LocalTime.now();
        if (bookingType.getStartTime().isAfter(currentTime)) {
            currentTime = bookingType.getStartTime();
        }

        checkInTime = LocalDateTime.now().with(DateTimeUtil.roundUpToNearestHalfHour(currentTime));
        checkOutTime = checkInTime.plusHours(hourAmount);
    }

    private void createHourListUI() {
        List<String> hours = DateTimeUtil.generateHourList(10);
        hourAdapter = new HourAdapter(hours);
        hourAdapter.setOnHourClickListener((position) -> {
            hourAdapter.setCurrentHour(hours.get(position));

            hourAmount = Integer.parseInt(hours.get(position).replace(AppConstant.HOUR_SUFFIX, ""));
            checkOutTime = checkInTime.plusHours(hourAmount);

        });
        binding.rvHourAmount.setAdapter(hourAdapter);
        binding.rvHourAmount.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.rvHourAmount.addItemDecoration(new SpaceItemDecoration(12, RecyclerView.HORIZONTAL));
    }

    private List<String> initTimeList() {
        LocalTime startTime;
        if (DateTimeUtil.isSameDay(currentCalendar, Calendar.getInstance())) {
            startTime = DateTimeUtil.roundUpToNearestHalfHour(LocalTime.now());
        } else {
            startTime = LocalTime.of(0, 0);
        }

        LocalTime endOfDay = LocalTime.of(23, 30);
        return DateTimeUtil.generateTimeList(startTime, endOfDay);
    }
}