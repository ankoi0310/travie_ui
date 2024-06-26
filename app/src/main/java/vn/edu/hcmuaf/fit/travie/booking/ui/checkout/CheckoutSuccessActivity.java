package vn.edu.hcmuaf.fit.travie.booking.ui.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import java.time.LocalDateTime;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModel;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModelFactory;
import vn.edu.hcmuaf.fit.travie.booking.ui.fragment.SelectedRoomFragment;
import vn.edu.hcmuaf.fit.travie.booking.ui.fragment.SelectedTimeFragment;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.BookingStatus;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.PaymentStatus;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AnimationUtil;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.BookingUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityCheckoutSuccessBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;

public class CheckoutSuccessActivity extends BaseActivity {
    ActivityCheckoutSuccessBinding binding;

    BookingViewModel bookingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutSuccessBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AnimationUtil.animateView(binding.loadingView.getRoot(), View.VISIBLE, 0.4f, 200);
        bookingViewModel = new BookingViewModelFactory(this).create(BookingViewModel.class);
        fetchCheckoutResult();
        handleCheckoutResult();
    }

    private void fetchCheckoutResult() {
        Intent intent = getIntent();
        int code = intent.getIntExtra("code", 0);
        if (code == 0) {
            Toast.makeText(this, R.string.invoice_code_invalid, Toast.LENGTH_SHORT).show();
        }

        bookingViewModel.completeCheckout(code);
    }

    private void handleCheckoutResult() {
        bookingViewModel.getCheckoutResult().observe(this, result -> {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if (result.getError() != null) {
                    Toast.makeText(this, result.getError(), Toast.LENGTH_SHORT).show();
                }

                if (result.getSuccess() != null) {
                    Invoice invoice = result.getSuccess();
                    binding.bookingStatusTxt.setText(BookingStatus.getResId(invoice.getBookingStatus()));
                    binding.descriptionTxt.setText(BookingUtil.generateDescription(invoice));
                    binding.codeTxt.setText(invoice.getCode());
                    binding.guestPhoneTxt.setText(invoice.getGuestPhone());
                    binding.guestNameTxt.setText(invoice.getGuestName());
                    initSelectedRoomFragment(invoice.getRoom());
                    initSelectedTimeFragment(invoice.getCheckIn(), invoice.getCheckOut(), invoice.getBookingType());
                    binding.paymentStatusTxt.setText(PaymentStatus.getResId(invoice.getPaymentStatus()));
                    binding.paymentMethodTxt.setText(invoice.getPaymentMethod().getLabelResId());
                    binding.totalPriceTxt.setText(AppUtil.formatCurrency(invoice.getTotalPrice()));
                    binding.promotionPriceTxt.setText(AppUtil.formatCurrency(invoice.getTotalPrice() - invoice.getFinalPrice()));
                    binding.finalPriceTxt.setText(AppUtil.formatCurrency(invoice.getFinalPrice()));
                }

                if (binding.loadingView.getRoot().getVisibility() == View.VISIBLE) {
                    AnimationUtil.animateView(binding.loadingView.getRoot(), View.GONE, 0, 200);
                }
            }, 1000);
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
}