package vn.edu.hcmuaf.fit.travie.invoice.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.BookingStatus;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.DateTimeUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderInvoiceBinding;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;
import vn.edu.hcmuaf.fit.travie.invoice.ui.invoicedetail.InvoiceDetailActivity;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {
    ViewHolderInvoiceBinding binding;
    Context context;
    private final ArrayList<Invoice> invoices;

    public InvoiceAdapter(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
    }

    @NonNull
    @Override
    public InvoiceAdapter.InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        binding = ViewHolderInvoiceBinding.inflate(LayoutInflater.from(context), parent, false);
        return new InvoiceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceAdapter.InvoiceViewHolder holder, int position) {
        Invoice invoice = invoices.get(position);

        setBookingStatus(holder, invoice.getBookingStatus());

        assert invoice.getRoom().getHotel() != null;
        holder.hotelTxt.setText(invoice.getRoom().getHotel().getName());
        holder.roomTxt.setText(invoice.getRoom().getName());
        holder.bookingTypeTxt.setText(invoice.getBookingType().getName());
        holder.codeTxt.setText(invoice.getCode());
        holder.dateTimeTxt.setText(DateTimeUtil.getDateTimeFormatter("HH:mm - dd/MM/yyyy").format(invoice.getModifiedDate()));
        holder.priceTxt.setText(AppUtil.formatCurrency(invoice.getFinalPrice()));

        initActionMenu(holder);

        holder.main.setOnClickListener(v -> {
            Intent intent = new Intent(context, InvoiceDetailActivity.class);
            intent.putExtra("invoice", invoice);
            context.startActivity(intent);
        });
    }

    private void setBookingStatus(InvoiceViewHolder holder, BookingStatus bookingStatus) {
        holder.bookingStatusTxt.setText(BookingStatus.getResId(bookingStatus));

        // Set background tint and text color based on booking status
        int backgroundTint, textColor;
        switch (bookingStatus) {
            case SUCCESS:
            case COMPLETED:
                backgroundTint = R.color.success_20;
                textColor = R.color.success_80;
                break;
            case REJECTED:
            case CANCELLED:
                backgroundTint = R.color.error_100;
                textColor = R.color.error;
                break;
            default:
                backgroundTint = R.color.info_100;
                textColor = R.color.info;
                break;
        }

        holder.bookingStatusTxt.setBackgroundTintList(AppCompatResources.getColorStateList(context, backgroundTint));
        holder.bookingStatusTxt.setTextColor(AppCompatResources.getColorStateList(context, textColor));
    }

    private void initActionMenu(@NonNull InvoiceAdapter.InvoiceViewHolder holder) {
        MenuInflater inflater = new MenuInflater(context);
        inflater.inflate(R.menu.invoice_detail_menu, holder.actionMenuView.getMenu());

        holder.actionMenuView.setOverflowIcon(AppCompatResources.getDrawable(context, R.drawable.arrow_right));
        holder.actionMenuView.setOnClickListener(v -> holder.actionMenuView.showContextMenu());
        holder.actionMenuView.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            return id == R.id.action_delete_booking_history;
        });
    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }

    public static class InvoiceViewHolder extends RecyclerView.ViewHolder {
        View main;
        TextView bookingStatusTxt, hotelTxt, roomTxt, bookingTypeTxt, codeTxt, dateTimeTxt, priceTxt;
        ActionMenuView actionMenuView;

        public InvoiceViewHolder(ViewHolderInvoiceBinding binding) {
            super(binding.getRoot());
            main = binding.main;
            bookingStatusTxt = binding.bookingStatusTxt;
            hotelTxt = binding.hotelTxt;
            roomTxt = binding.roomTxt;
            bookingTypeTxt = binding.bookingTypeTxt;
            codeTxt = binding.codeTxt;
            dateTimeTxt = binding.dateTimeTxt;
            priceTxt = binding.priceTxt;
            actionMenuView = binding.actionMenuView;
        }
    }
}
