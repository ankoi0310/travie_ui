package vn.edu.hcmuaf.fit.travie.hotel.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.hotel.model.Hotel;

public interface HotelService {
    @GET("hotel/search")
    Call<HttpResponse<List<Hotel>>> getHotels();
}
