package vn.edu.hcmuaf.fit.travie.invoice.ui.history.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.invoice.BookingStatus;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.DateTimeUtil;
import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderInvoiceBinding;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {
    ViewHolderInvoiceBinding binding;
    Context context;
    ArrayList<Invoice> invoices = new ArrayList<>();

    @NonNull
    @Override
    public InvoiceAdapter.InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        binding = ViewHolderInvoiceBinding.inflate(LayoutInflater.from(context), parent, false);
        return new InvoiceViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setInvoices(ArrayList<Invoice> invoices) {
        this.invoices = invoices;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceAdapter.InvoiceViewHolder holder, int position) {
        Invoice invoice = invoices.get(position);

        setBookingStatus(holder, invoice.getBookingStatus());

        assert invoice.getRoom().getHotel() != null;
        holder.binding.hotelTxt.setText(invoice.getRoom().getHotel().getName());
        holder.binding.roomTxt.setText(invoice.getRoom().getName());
        holder.binding.bookingTypeTxt.setText(invoice.getBookingType().getName());
        holder.binding.codeTxt.setText(invoice.getCode());
        holder.binding.dateTimeTxt.setText(DateTimeUtil.getDateTimeFormatter("HH:mm - dd/MM/yyyy").format(invoice.getModifiedDate()));
        holder.binding.priceTxt.setText(AppUtil.formatCurrency(invoice.getFinalPrice()));

        initActionMenu(holder);
    }

    private void setBookingStatus(InvoiceViewHolder holder, BookingStatus bookingStatus) {
        holder.binding.bookingStatusTxt.setText(BookingStatus.getResId(bookingStatus));

        // Set background tint and text color based on booking status
        int backgroundTint, textColor;
        switch (bookingStatus) {
            case CONFIRMED:
            case COMPLETED:
                backgroundTint = R.color.success_20;
                textColor = R.color.success_80;
                break;
            case REJECTED:
                backgroundTint = R.color.error_100;
                textColor = R.color.error;
                break;
            case CANCELLED:
                backgroundTint = R.color.error_100;
                textColor = R.color.error;
                break;
            default:
                backgroundTint = R.color.info_100;
                textColor = R.color.info;
                break;
        }

        holder.binding.bookingStatusTxt.setBackgroundTintList(AppCompatResources.getColorStateList(context, backgroundTint));
        holder.binding.bookingStatusTxt.setTextColor(AppCompatResources.getColorStateList(context, textColor));
    }

    private void initActionMenu(@NonNull InvoiceAdapter.InvoiceViewHolder holder) {
        MenuInflater inflater = new MenuInflater(holder.binding.getRoot().getContext());
        inflater.inflate(R.menu.invoice_detail_menu, holder.binding.actionMenuView.getMenu());

        holder.binding.actionMenuView.setOverflowIcon(AppCompatResources.getDrawable(context, R.drawable.arrow_right));
        holder.binding.actionMenuView.setOnClickListener(v -> holder.binding.actionMenuView.showContextMenu());
        holder.binding.actionMenuView.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            return id == R.id.action_delete_booking_history;
        });
    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }

    public static class InvoiceViewHolder extends RecyclerView.ViewHolder {
        ViewHolderInvoiceBinding binding;

        public InvoiceViewHolder(ViewHolderInvoiceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
