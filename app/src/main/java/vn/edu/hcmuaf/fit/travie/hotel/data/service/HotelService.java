package vn.edu.hcmuaf.fit.travie.hotel.data.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.paging.Page;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;

public interface HotelService {
    @GET("hotel/search")
    Call<HttpResponse<Page<Hotel>>> search(@Query("keyword") String keyword,
                                           @Query("page") Integer page,
                                           @Query("size") Integer size);

    @GET("hotel/nearby")
    Call<HttpResponse<Page<Hotel>>> getNearByHotelList(@Query("location") String location,
                                                       @Query("page") Integer page,
                                                       @Query("size") Integer size);

    @GET("hotel/popular")
    Call<HttpResponse<Page<Hotel>>> getPopularHotelList(@Query("page") Integer page,
                                                        @Query("size") Integer size);

    @GET("hotel/explore")
    Call<HttpResponse<Page<Hotel>>> getExploreHotelList();

    @GET("hotel/{id}")
    Call<HttpResponse<Hotel>> getHotelById(@Path("id") long id);
}
