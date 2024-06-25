package vn.edu.hcmuaf.fit.travie.booking.ui.checkout;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.time.LocalDateTime;
import java.util.Optional;

import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;
import vn.edu.hcmuaf.fit.travie.booking.data.service.BookingRequestHolder;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModel;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModelFactory;
import vn.edu.hcmuaf.fit.travie.booking.ui.fragment.SelectedRoomFragment;
import vn.edu.hcmuaf.fit.travie.booking.ui.fragment.SelectedTimeFragment;
import vn.edu.hcmuaf.fit.travie.booking.ui.payment.ChoosePaymentMethodActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.cancellationpolicy.CancellationPolicyFragment;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.PaymentMethod;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityCheckoutBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;

public class CheckoutActivity extends BaseActivity {
    ActivityCheckoutBinding binding;

    BookingViewModel bookingViewModel;
    BookingRequestHolder bookingRequestHolder = BookingRequestHolder.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Handle the incoming deep link
        handleDeepLink();

        bookingViewModel = new BookingViewModelFactory(this).create(BookingViewModel.class);

        handleBookingRequest();

        binding.toolbar.setNavigationOnClickListener(v -> finish());
        ActivityResultLauncher<Intent> activityResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            PaymentMethod paymentMethod = intent.getSerializableExtra("paymentMethod", PaymentMethod.class);
                            if (paymentMethod != null) {
                                binding.choosePaymentMethodTxt.setText(paymentMethod.getLabelResId());
                            }
                        }
                    }
                });
        binding.choosePaymentMethodTxt.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChoosePaymentMethodActivity.class);
            activityResult.launch(intent);
        });

        initCancelationPolicyFragment();

        binding.checkoutBtn.setOnClickListener(v -> {
            BookingRequest bookingRequest = bookingRequestHolder.getBookingRequest();
            bookingRequest.setGuestName(binding.guestNameTxt.getText().toString());
            bookingRequest.setGuestPhone(binding.guestPhoneTxt.getText().toString());
            bookingViewModel.checkout();
        });
        bookingViewModel.getBookingResult().observe(this, result -> {
            if (result.getError() != null) {
                Toast.makeText(this, result.getError(), Toast.LENGTH_SHORT).show();
                return;
            }

            if (result.getSuccess() != null) {
                Log.d("CheckoutActivity", "Checkout success: " + result.getSuccess().getCheckoutUrl());
//                LinkCreationResponse linkCreationResponse = result.getSuccess();
//                openWebPage(linkCreationResponse.getCheckoutUrl());
            }
        });
    }

    private void handleDeepLink() {
        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            Optional<String> path = data.getPathSegments().stream().findFirst();

            if (path.isPresent()) {
                switch (path.get()) {
                    case "checkout-cancel":
//                        Intent checkoutCancelIntent = new Intent(this, CheckoutFailActivity.class);
//                        startActivity(checkoutCancelIntent);
                        break;
                    case "checkout-success":
//                        Intent successIntent = new Intent(this, CheckoutSuccessActivity.class);
//                        successIntent.putExtra("orderCode", data.getQueryParameter("orderCode"));
//                        startActivity(successIntent);
                        break;
                }
            }
        }
    }

    public void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(this, "No application can handle this request. Please install a web browser", Toast.LENGTH_LONG).show();
        }
    }

    private void handleBookingRequest() {
        BookingRequest bookingRequest = bookingRequestHolder.getBookingRequest();
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