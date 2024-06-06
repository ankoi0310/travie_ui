package vn.edu.hcmuaf.fit.travie.hotel.data.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.hcmuaf.fit.travie.core.common.model.BaseModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Hotel extends BaseModel {
    private String name;
    private String introduction;
    private Address address;
    private List<BookingType> bookingTypes;
    private List<Room> rooms;
    private List<String> images;
    private List<Amenity> amenities;
    private HotelStatus status;
    private double rating;
    private List<Review> reviews;

    public enum HotelStatus {
        ACTIVE, INACTIVE, DELETED
    }
}
