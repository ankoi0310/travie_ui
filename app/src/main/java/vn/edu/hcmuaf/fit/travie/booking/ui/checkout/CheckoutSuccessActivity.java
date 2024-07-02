package vn.edu.hcmuaf.fit.travie.booking.ui.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDateTime;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModel;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModelFactory;
import vn.edu.hcmuaf.fit.travie.booking.ui.fragment.SelectedRoomFragment;
import vn.edu.hcmuaf.fit.travie.booking.ui.fragment.SelectedTimeFragment;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.MainActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.cancellationpolicy.CancellationPolicyFragment;
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

        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets stautusBars = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            // unit of stautusBars.top is px, so we need to convert it to dp
            int statusBarHeight = stautusBars.top / (getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
            binding.toolbar.setPadding(0, statusBarHeight + binding.toolbar.getPaddingBottom(), 0,  binding.toolbar.getPaddingBottom());
            binding.toolbar.setTitleMarginTop(statusBarHeight - 4);
            v.setPadding(0, 0, 0, stautusBars.bottom);
            return WindowInsetsCompat.CONSUMED;
        });

        AnimationUtil.animateView(binding.loadingView.getRoot(), View.VISIBLE, 0.4f, 200);
        bookingViewModel = new ViewModelProvider(this, new BookingViewModelFactory(this)).get(BookingViewModel.class);
        fetchCheckoutResult();
        handleCheckoutResult();

        binding.toolbar.setNavigationOnClickListener(v -> backToHome());
    }

    @Override
    public boolean onSupportNavigateUp() {
        backToHome();
        return true;
    }

    // TODO: Maybe handle return to error page
    private void backToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void fetchCheckoutResult() {
        Intent intent = getIntent();
        int orderCode = intent.getIntExtra("orderCode", 0);
        if (orderCode == 0) {
            Toast.makeText(this, R.string.invoice_code_invalid, Toast.LENGTH_SHORT).show();

            if (binding.loadingView.getRoot().getVisibility() == View.VISIBLE) {
                AnimationUtil.animateView(binding.loadingView.getRoot(), View.GONE, 0, 200);
            }
            backToHome();
            return;
        }

        bookingViewModel.completeCheckout(orderCode);
    }

    private void handleCheckoutResult() {
        bookingViewModel.getCheckoutResult().observe(this, result -> {
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
                initCancelationPolicyFragment();
            }

            if (binding.loadingView.getRoot().getVisibility() == View.VISIBLE) {
                AnimationUtil.animateView(binding.loadingView.getRoot(), View.GONE, 0, 200);
            }
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