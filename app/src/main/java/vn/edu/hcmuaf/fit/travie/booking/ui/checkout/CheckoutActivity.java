package vn.edu.hcmuaf.fit.travie.booking.ui.checkout;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import java.time.LocalDateTime;

import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;
import vn.edu.hcmuaf.fit.travie.booking.data.model.LinkCreationResponse;
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

    private boolean isResumedFromBrowser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                LinkCreationResponse linkCreationResponse = result.getSuccess();
                openWebPage(linkCreationResponse.getCheckoutUrl());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isResumedFromBrowser) {
            isResumedFromBrowser = false;
            Intent intent = new Intent(this, CheckoutFailActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void openWebPage(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);

        isResumedFromBrowser = true;
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