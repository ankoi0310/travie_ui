package vn.edu.hcmuaf.fit.travie.invoice.ui.history;

import java.util.ArrayList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;

@Getter
@RequiredArgsConstructor
public class InvoiceResult {
    private final ArrayList<Invoice> success;
    private final String error;
}
