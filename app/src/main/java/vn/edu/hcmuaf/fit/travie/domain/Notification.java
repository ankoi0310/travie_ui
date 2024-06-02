package vn.edu.hcmuaf.fit.travie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    private String title;
    private String content;
    private String type;
    private String status;
    private AppUser user;
    private Invoice invoice;
}
