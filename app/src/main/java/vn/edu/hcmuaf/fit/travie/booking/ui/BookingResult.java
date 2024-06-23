package vn.edu.hcmuaf.fit.travie.booking.ui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;

@Getter
@AllArgsConstructor
public class BookingResult {
    private Invoice success;
    private String error;
}
