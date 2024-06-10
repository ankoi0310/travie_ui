package vn.edu.hcmuaf.fit.travie.invoice.ui.history;

import androidx.annotation.Nullable;

import java.util.List;

import lombok.Getter;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;

@Getter
public class InvoiceResult {
    @Nullable
    private final List<Invoice> success;

    @Nullable
    private final String error;

    public InvoiceResult(@Nullable List<Invoice> success) {
        this.success = success;
        this.error = null;
    }

    public InvoiceResult(@Nullable String error) {
        this.success = null;
        this.error = error;
    }
}
