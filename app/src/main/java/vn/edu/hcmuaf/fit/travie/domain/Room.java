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
public class Room {
    private String name;
    private String description;
    private int price;
    private int capacity;
    private List<Amenity> amenities;
    private List<String> imageUrl;
}
