package vn.edu.hcmuaf.fit.travie.booking.ui.payment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.Insets;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.Objects;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;
import vn.edu.hcmuaf.fit.travie.booking.data.service.BookingRequestHolder;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.PaymentMethod;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityChoosePaymentMethodBinding;

public class ChoosePaymentMethodActivity extends BaseActivity {
    ActivityChoosePaymentMethodBinding binding;
    BookingRequestHolder bookingRequestHolder = BookingRequestHolder.getInstance();
    List<RadioButton> radioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChoosePaymentMethodBinding.inflate(getLayoutInflater());
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

        radioButtons = List.of(binding.rbATM, binding.rbMomo);

        setSelectedPaymentMethod();

        Drawable uncheckDrawable = getUncheckedDrawable();
        Drawable checkedDrawable = getCheckedDrawable();
        binding.paymentMethodGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = findViewById(checkedId);
            for (RadioButton rb : radioButtons) {
                rb.setCompoundDrawablesWithIntrinsicBounds(null, null, rb == radioButton ? checkedDrawable : uncheckDrawable, null);
            }
        });

        binding.submitBtn.setOnClickListener(v -> {
            int resId = binding.paymentMethodGroup.getCheckedRadioButtonId();
            PaymentMethod paymentMethod = PaymentMethod.fromResId(resId);

            BookingRequest bookingRequest = bookingRequestHolder.getBookingRequest();
            if (bookingRequest != null) {
                bookingRequest.setPaymentMethod(paymentMethod);
                bookingRequestHolder.setBookingRequest(bookingRequest);
            }

            setResult(RESULT_OK, new Intent().putExtra("paymentMethod", paymentMethod));
            finish();
        });
    }

    private void setSelectedPaymentMethod() {
        BookingRequest bookingRequest = bookingRequestHolder.getBookingRequest();
        if (bookingRequest != null) {
            PaymentMethod paymentMethod = bookingRequest.getPaymentMethod();
            if (paymentMethod != null) {
                if (paymentMethod == PaymentMethod.ATM) {
                    binding.rbATM.setChecked(true);
                    binding.rbATM.setCompoundDrawablesWithIntrinsicBounds(null, null, getCheckedDrawable(), null);
                } else if (paymentMethod == PaymentMethod.MOMO) {
                    binding.rbMomo.setChecked(true);
                    binding.rbMomo.setCompoundDrawablesWithIntrinsicBounds(null, null, getCheckedDrawable(), null);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            PaymentMethod paymentMethod = data.getSerializableExtra("paymentMethod", PaymentMethod.class);
            if (paymentMethod != null) {
                if (paymentMethod == PaymentMethod.ATM) {
                    binding.rbATM.setChecked(true);
                } else if (paymentMethod == PaymentMethod.MOMO) {
                    binding.rbMomo.setChecked(true);
                }
            }
        }
    }

    private Drawable getCheckedDrawable() {
        @NonNull Drawable drawable = Objects.requireNonNull(AppCompatResources.getDrawable(this, R.drawable.tick_circle_bold));
        DrawableCompat.setTintList(drawable, AppCompatResources.getColorStateList(this, R.color.primary));
        return drawable;
    }

    private Drawable getUncheckedDrawable() {
        @NonNull Drawable drawable = Objects.requireNonNull(AppCompatResources.getDrawable(this, R.drawable.tick_circle));
        DrawableCompat.setTintList(drawable, AppCompatResources.getColorStateList(this, R.color.black));
        return drawable;
    }
}