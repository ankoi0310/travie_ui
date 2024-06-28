package vn.edu.hcmuaf.fit.travie.booking.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;

@Getter
@RequiredArgsConstructor
public class CheckoutResult {
    private final Invoice success;
    private final String error;
}
