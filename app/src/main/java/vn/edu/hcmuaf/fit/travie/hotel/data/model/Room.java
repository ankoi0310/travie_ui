package vn.edu.hcmuaf.fit.travie.hotel.data.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.domain.BaseModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room extends BaseModel {
    private String name;
    private String description;
    private int price;
    private List<Amenity> amenities;
    private List<RoomImage> roomImages;
}
