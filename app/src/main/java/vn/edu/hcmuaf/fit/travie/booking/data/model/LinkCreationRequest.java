package vn.edu.hcmuaf.fit.travie.booking.data.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkCreationRequest {
    private int orderCode;
    private int amount;
    private String description;
    private String buyerName;
    private String buyerEmail;
    private String buyerPhone;
    private String buyerAddress;
    private List<LinkCreationItem> items;
    private String cancelUrl;
    private String returnUrl;
    private int expiredAt;
    private String signature;
}
