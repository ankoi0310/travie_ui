package vn.edu.hcmuaf.fit.travie.booking.ui.checkout;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDateTime;

import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModel;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModelFactory;
import vn.edu.hcmuaf.fit.travie.booking.ui.fragment.SelectedRoomFragment;
import vn.edu.hcmuaf.fit.travie.booking.ui.fragment.SelectedTimeFragment;
import vn.edu.hcmuaf.fit.travie.booking.ui.payment.ChoosePaymentMethodActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.cancellationpolicy.CancellationPolicyFragment;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityCheckoutBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;

public class CheckoutActivity extends BaseActivity {
    ActivityCheckoutBinding binding;

    BookingViewModel bookingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bookingViewModel = new ViewModelProvider(this, new BookingViewModelFactory(this)).get(BookingViewModel.class);

        handleBookingRequest();

        Intent intent = getIntent();
        if (intent.hasExtra("room")) {
            Room room = intent.getParcelableExtra("room");
            initSelectedRoomFragment(room);
        }

        LocalDateTime checkIn = LocalDateTime.now();
        LocalDateTime checkOut = LocalDateTime.now().plusDays(1);
        BookingType bookingType = BookingType.demo1();
        initSelectedTimeFragment(checkIn, checkOut, bookingType);

        binding.choosePaymentMethodTxt.setOnClickListener(v -> {
            Intent choosePaymentMethodIntent = new Intent(this, ChoosePaymentMethodActivity.class);
            startActivity(choosePaymentMethodIntent);
        });

        initCancelationPolicyFragment();
    }

    private void handleBookingRequest() {
        bookingViewModel.getBookingRequest().observe(this, bookingRequest -> {
            if (bookingRequest.getRoom() != null) {
                initSelectedRoomFragment(bookingRequest.getRoom());
                binding.originalPriceTxt.setText(String.valueOf(bookingRequest.getRoom().getOneDayOrigin()));
            }

            if (bookingRequest.getCheckIn() != null && bookingRequest.getCheckOut() != null && bookingRequest.getBookingType() != null) {
                initSelectedTimeFragment(bookingRequest.getCheckIn(), bookingRequest.getCheckOut(), bookingRequest.getBookingType());
            }

            if (bookingRequest.getGuestPhone() != null) {
                binding.guestPhoneTxt.setText(bookingRequest.getGuestPhone());
            }

            if (bookingRequest.getGuestName() != null) {
                binding.guestNameTxt.setText(bookingRequest.getGuestName());
            }

            if (bookingRequest.getPaymentMethod() != null) {
                binding.choosePaymentMethodTxt.setText(bookingRequest.getPaymentMethod().getLabelResId());
            }

            binding.totalPriceTxt.setText(AppUtil.formatCurrency(bookingRequest.getTotalPrice()));
            binding.finalPriceTxt1.setText(AppUtil.formatCurrency(bookingRequest.getTotalPrice()));
            binding.finalPriceTxt2.setText(AppUtil.formatCurrency(bookingRequest.getTotalPrice()));
        });
    }

    private void initSelectedRoomFragment(Room room) {
        getSupportFragmentManager().beginTransaction()
                .replace(binding.selectedRoomFragment.getId(), SelectedRoomFragment.newInstance(room))
                .commit();
    }

    private void initSelectedTimeFragment(LocalDateTime checkIn, LocalDateTime checkOut, BookingType bookingType) {
        getSupportFragmentManager().beginTransaction()
                .replace(binding.selectedTimeFragment.getId(), SelectedTimeFragment.newInstance(checkIn, checkOut, bookingType))
                .commit();
    }

    private void initCancelationPolicyFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(binding.cancellationPolicyFragment.getId(), CancellationPolicyFragment.newInstance())
                .commit();
    }
}