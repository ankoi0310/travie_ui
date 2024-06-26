package vn.edu.hcmuaf.fit.travie.invoice.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;

@Getter
@RequiredArgsConstructor
public class InvoiceResult {
    private final Invoice success;
    private final String error;
}
