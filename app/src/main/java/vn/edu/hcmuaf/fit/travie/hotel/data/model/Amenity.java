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
public class Amenity extends BaseModel {
    private String name;
    private boolean deleted;
}
