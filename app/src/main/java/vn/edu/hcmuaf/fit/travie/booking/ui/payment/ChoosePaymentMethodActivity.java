package vn.edu.hcmuaf.fit.travie.booking.ui.payment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModel;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModelFactory;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.PaymentMethod;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityChoosePaymentMethodBinding;

public class ChoosePaymentMethodActivity extends BaseActivity {
    ActivityChoosePaymentMethodBinding binding;
    List<RadioButton> radioButtons;

    @Inject
    BookingViewModel bookingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChoosePaymentMethodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bookingViewModel = new ViewModelProvider(this, new BookingViewModelFactory(this)).get(BookingViewModel.class);

        radioButtons = List.of(binding.rbATM, binding.rbMomo);

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
            Objects.requireNonNull(bookingViewModel.getBookingRequest().getValue()).setPaymentMethod(PaymentMethod.fromResId(resId));
            finish();
        });
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