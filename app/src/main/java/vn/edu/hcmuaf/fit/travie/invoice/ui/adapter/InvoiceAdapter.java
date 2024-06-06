package vn.edu.hcmuaf.fit.travie.invoice.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.edu.hcmuaf.fit.travie.databinding.ViewHolderInvoiceBinding;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.InvoiceViewHolder> {
    List<Invoice> invoices;

    @NonNull
    @Override
    public InvoiceAdapter.InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolderInvoiceBinding binding = ViewHolderInvoiceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new InvoiceViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InvoiceAdapter.InvoiceViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class InvoiceViewHolder extends RecyclerView.ViewHolder {
        ViewHolderInvoiceBinding binding;

        public InvoiceViewHolder(ViewHolderInvoiceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
