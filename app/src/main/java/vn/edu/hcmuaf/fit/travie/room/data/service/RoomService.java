package vn.edu.hcmuaf.fit.travie.room.data.service;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Room;

public interface RoomService {
    @GET("room/search")
    Call<HttpResponse<ArrayList<Room>>> search(@Query("hotelId") Long hotelId, @Query("applyFlashSale") Boolean applyFlashSale);

    @GET("room/{id}")
    Call<HttpResponse<Room>> getRoomById(@Path("id") int id);
}
