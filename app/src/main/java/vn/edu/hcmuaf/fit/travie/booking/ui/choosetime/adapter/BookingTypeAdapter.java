package vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import java.util.List;

import vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.ChooseTimeByDayFragment;
import vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.ChooseTimeByHourFragment;
import vn.edu.hcmuaf.fit.travie.booking.ui.choosetime.ChooseTimeOvernightFragment;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.TimeUnit;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;

public class BookingTypeAdapter extends FragmentStateAdapter {
    private final List<BookingType> bookingTypes;

    public BookingTypeAdapter(@NonNull FragmentActivity fragmentActivity, List<BookingType> bookingTypes) {
        super(fragmentActivity);
        this.bookingTypes = bookingTypes;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return switch (position) {
            case 0 -> {
                BookingType hourBookingType = bookingTypes.stream().filter(bookingType -> bookingType.getUnit() == TimeUnit.HOUR).findFirst().orElse(null);
                yield ChooseTimeByHourFragment.newInstance(hourBookingType);
            }
            case 1 -> {
                BookingType overnightBookingType = bookingTypes.stream().filter(bookingType -> bookingType.getUnit() == TimeUnit.OVERNIGHT).findFirst().orElse(null);
                yield ChooseTimeOvernightFragment.newInstance(overnightBookingType);
            }
            case 2 -> {
                BookingType dayBookingType = bookingTypes.stream().filter(bookingType -> bookingType.getUnit() == TimeUnit.DAY).findFirst().orElse(null);
                yield ChooseTimeByDayFragment.newInstance(dayBookingType);
            }
            default -> throw new IllegalStateException("Unexpected value: " + position);
        };
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
