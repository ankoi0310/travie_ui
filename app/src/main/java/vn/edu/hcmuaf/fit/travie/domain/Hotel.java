package vn.edu.hcmuaf.fit.travie.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    private String name;
    private String address;
    private AppUser owner;
    private List<Amenity> amenities;
    private List<Room> rooms;
}
