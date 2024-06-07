package vn.edu.hcmuaf.fit.travie.hotel.data.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.HotelModel;

public interface HotelService {
    @GET("hotel/search")
    Call<HttpResponse<List<HotelModel>>> getHotels();
}
