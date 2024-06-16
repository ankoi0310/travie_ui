package vn.edu.hcmuaf.fit.travie.hotel.data.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;

public interface HotelService {
    @GET("hotel/search")
    Call<HttpResponse<List<Hotel>>> searchHotel();

    @GET("hotel/nearby")
    Call<HttpResponse<List<Hotel>>> getNearByHotelList();

    @GET("hotel/recommend")
    Call<HttpResponse<List<Hotel>>> getRecommendHotelList();

    @GET("hotel/popular")
    Call<HttpResponse<List<Hotel>>> getPopularHotelList();

    @GET("hotel/explore")
    Call<HttpResponse<List<Hotel>>> getExploreHotelList();
}
