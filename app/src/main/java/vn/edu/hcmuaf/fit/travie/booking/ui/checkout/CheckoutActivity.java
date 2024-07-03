package vn.edu.hcmuaf.fit.travie.booking.ui.checkout;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDateTime;

import vn.edu.hcmuaf.fit.travie.auth.ui.login.LoginActivity;
import vn.edu.hcmuaf.fit.travie.booking.data.model.BookingRequest;
import vn.edu.hcmuaf.fit.travie.booking.data.model.LinkCreationResponse;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModel;
import vn.edu.hcmuaf.fit.travie.booking.ui.BookingViewModelFactory;
import vn.edu.hcmuaf.fit.travie.booking.ui.fragment.SelectedRoomFragment;
import vn.edu.hcmuaf.fit.travie.booking.ui.fragment.SelectedTimeFragment;
import vn.edu.hcmuaf.fit.travie.booking.ui.payment.ChoosePaymentMethodActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.common.ui.cancellationpolicy.CancellationPolicyFragment;
import vn.edu.hcmuaf.fit.travie.core.service.AuthManager;
import vn.edu.hcmuaf.fit.travie.core.service.LocalStorage;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.PaymentMethod;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AnimationUtil;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityCheckoutBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;

public class CheckoutActivity extends BaseActivity {
    ActivityCheckoutBinding binding;

    BookingViewModel bookingViewModel;
    BookingRequest bookingRequest = BookingRequest.getInstance();

    private boolean isResumedFromBrowser = false;

    AuthManager authManager;
    LocalStorage localStorage;

    LinkCreationResponse linkCreationResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authManager = new AuthManager(this);
        if (!authManager.isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        localStorage = new LocalStorage(this);
        binding.guestNameTxt.setText(localStorage.getString(LocalStorage.USER_NICKNAME));
        binding.guestPhoneTxt.setText(localStorage.getString(LocalStorage.USER_PHONE));

        bookingViewModel = new ViewModelProvider(this, new BookingViewModelFactory(this)).get(BookingViewModel.class);

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
            PaymentMethod paymentMethod = bookingRequest.getPaymentMethod();
            if (paymentMethod == null) {
                Toast.makeText(this, "Vui lòng chọn phương thức thanh toán", Toast.LENGTH_SHORT).show();
                return;
            }

            if (paymentMethod != PaymentMethod.ATM) {
                Toast.makeText(this, "Phương thức thanh toán chưa được hỗ trợ", Toast.LENGTH_SHORT).show();
                return;
            }

            bookingRequest.setGuestName(binding.guestNameTxt.getText().toString());
            bookingRequest.setGuestPhone(binding.guestPhoneTxt.getText().toString());

            AnimationUtil.animateView(binding.loadingView.getRoot(), View.VISIBLE, 0.4f, 200);
            bookingViewModel.checkout();
        });
        bookingViewModel.getBookingResult().observe(this, result -> {
            if (result.getError() != null) {
                Toast.makeText(this, result.getError(), Toast.LENGTH_SHORT).show();
                return;
            }

            if (result.getSuccess() != null) {
                linkCreationResponse = result.getSuccess();
                openWebPage(linkCreationResponse.getCheckoutUrl());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isResumedFromBrowser && linkCreationResponse != null) {
            isResumedFromBrowser = false;
            Intent intent = new Intent(this, CheckoutFailActivity.class);
            intent.putExtra("orderCode", linkCreationResponse.getOrderCode());
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