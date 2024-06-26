package vn.edu.hcmuaf.fit.travie.booking.ui.checkout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModel;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModelFactory;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.MainActivity;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.BookingStatus;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.PaymentStatus;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AnimationUtil;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.BookingUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityCheckoutFailBinding;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;

public class CheckoutFailActivity extends BaseActivity {
    ActivityCheckoutFailBinding binding;

    BookingViewModel bookingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutFailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AnimationUtil.animateView(binding.loadingView.getRoot(), View.VISIBLE, 0.4f, 200);
        bookingViewModel = new BookingViewModelFactory(this).create(BookingViewModel.class);
        fetchCheckoutResult();
        handleCheckoutResult();

        handleReturnToHome();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        return true;
    }

    // return to home screen
    private void handleReturnToHome() {
        binding.toolbar.setNavigationOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
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
}