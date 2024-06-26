package vn.edu.hcmuaf.fit.travie.invoice.ui.invoicedetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import java.time.LocalDateTime;

import vn.edu.hcmuaf.fit.travie.booking.ui.fragment.SelectedRoomFragment;
import vn.edu.hcmuaf.fit.travie.booking.ui.fragment.SelectedTimeFragment;
import vn.edu.hcmuaf.fit.travie.core.common.ui.BaseActivity;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.BookingStatus;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.PaymentStatus;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AnimationUtil;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.BookingUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ActivityInvoiceDetailBinding;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.BookingType;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;
import vn.edu.hcmuaf.fit.travie.invoice.ui.InvoiceViewModel;
import vn.edu.hcmuaf.fit.travie.invoice.ui.InvoiceViewModelFactory;
import vn.edu.hcmuaf.fit.travie.room.data.model.Room;

public class InvoiceDetailActivity extends BaseActivity {
    ActivityInvoiceDetailBinding binding;

    InvoiceViewModel invoiceViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInvoiceDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AnimationUtil.animateView(binding.loadingView.getRoot(), View.VISIBLE, 0.4f, 200);

        invoiceViewModel = new InvoiceViewModelFactory(this).create(InvoiceViewModel.class);
        invoiceViewModel.getInvoice().observe(this, result -> new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (result.getError() != null) {
                Toast.makeText(this, result.getError(), Toast.LENGTH_SHORT).show();
            }

            if (result.getSuccess() != null) {
                updateUI(result.getSuccess());
            }

            if (binding.swipeRefreshLayout.isRefreshing()) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            if (binding.loadingView.getRoot().getVisibility() == View.VISIBLE) {
                AnimationUtil.animateView(binding.loadingView.getRoot(), View.GONE, 0, 200);
            }
        }, 1000));

        Intent intent = getIntent();
        Invoice invoice = intent.getParcelableExtra("invoice", Invoice.class);
        if (invoice == null) {
            Toast.makeText(this, "Không tìm thấy hóa đơn", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            updateUI(invoice);
            binding.swipeRefreshLayout.setOnRefreshListener(() -> invoiceViewModel.fetchInvoiceById(invoice.getId()));
        }
    }

    private void updateUI(Invoice invoice) {
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