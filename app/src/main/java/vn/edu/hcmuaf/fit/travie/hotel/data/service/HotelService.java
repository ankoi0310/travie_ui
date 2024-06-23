package vn.edu.hcmuaf.fit.travie.hotel.data.service;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;

public interface HotelService {
    @GET("hotel/search")
    Call<HttpResponse<ArrayList<Hotel>>> searchHotel();

    @GET("hotel/nearby")
    Call<HttpResponse<ArrayList<Hotel>>> getNearByHotelList(@Query("location") String location);

    @GET("hotel/popular")
    Call<HttpResponse<ArrayList<Hotel>>> getPopularHotelList();

    @GET("hotel/explore")
    Call<HttpResponse<ArrayList<Hotel>>> getExploreHotelList();

    @GET("hotel/{id}")
    Call<HttpResponse<Hotel>> getHotelById(@Path("id") long id);
}
