package vn.edu.hcmuaf.fit.travie.invoice.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderInvoiceByMonthBinding;

public class InvoiceByMonthAdapter extends RecyclerView.Adapter<InvoiceByMonthAdapter.InvoiceByMonthViewHolder> {
    @NonNull
    @Override
    public InvoiceByMonthAdapter.InvoiceByMonthViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceByMonthAdapter.InvoiceByMonthViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class InvoiceByMonthViewHolder extends RecyclerView.ViewHolder {
        ViewHolderInvoiceByMonthBinding binding;

        public InvoiceByMonthViewHolder(ViewHolderInvoiceByMonthBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
