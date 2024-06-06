package vn.edu.hcmuaf.fit.travie.hotel.data.datasource.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;

public interface HotelApi {

    @GET("hotel/search")
    Call<List<Hotel>> getHotels();
}
