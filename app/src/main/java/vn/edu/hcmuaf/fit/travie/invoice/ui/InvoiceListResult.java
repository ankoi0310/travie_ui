package vn.edu.hcmuaf.fit.travie.invoice.ui;

import java.util.ArrayList;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;

@Getter
@RequiredArgsConstructor
public class InvoiceListResult {
    private final ArrayList<Invoice> success;
    private final String error;
}
