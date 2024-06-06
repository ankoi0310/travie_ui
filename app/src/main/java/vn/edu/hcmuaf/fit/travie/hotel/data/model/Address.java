package vn.edu.hcmuaf.fit.travie.hotel.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.common.model.BaseModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address extends BaseModel {
    private String detail;
    private int wardId;
    private int districtId;
    private int provinceId;
    private String fullAddress;
}
