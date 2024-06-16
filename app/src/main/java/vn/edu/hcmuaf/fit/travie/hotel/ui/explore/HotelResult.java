package vn.edu.hcmuaf.fit.travie.hotel.ui.explore;

import androidx.annotation.Nullable;

import java.util.List;

import lombok.Getter;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;

@Getter
public class HotelResult {
    @Nullable
    private final List<Hotel> success;

    @Nullable
    private final String error;

    public HotelResult(@Nullable List<Hotel> success) {
        this.success = success;
        this.error = null;
    }

    public HotelResult(@Nullable String error) {
        this.success = null;
        this.error = error;
    }
}
