package vn.edu.hcmuaf.fit.travie.hotel.data.datasource.network;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.core.handler.Result;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.handler.error.DataError;
import vn.edu.hcmuaf.fit.travie.core.handler.ResultCallback;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.HotelModel;
import vn.edu.hcmuaf.fit.travie.hotel.data.service.HotelService;

@Singleton
public class HotelRemoteDataSource {
    private final HotelService hotelService;

    @Inject
    public HotelRemoteDataSource(RetrofitService retrofitService) {
        this.hotelService = retrofitService.createService(HotelService.class, null);
    }

    public void getHotelList(final ResultCallback<List<HotelModel>, DataError> callback) {
        Call<HttpResponse<List<HotelModel>>> call = hotelService.getHotels();
        call.enqueue(new Callback<HttpResponse<List<HotelModel>>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<List<HotelModel>>> call, @NonNull Response<HttpResponse<List<HotelModel>>> response) {
                Log.d("HotelRemoteDataSource", "onResponse: " + response.body());
                if (!response.isSuccessful()) {
                    callback.onComplete(new Result.Error<>(DataError.NETWORK.UNKNOWN));
                }

                if (response.body() == null) {
                    callback.onComplete(new Result.Error<>(DataError.NETWORK.UNKNOWN));
                }

                HttpResponse<List<HotelModel>> httpResponse = response.body();
                if (!httpResponse.isSuccess()) {
                    callback.onComplete(new Result.Error<>(httpResponse.getMessage()));
                }

                callback.onComplete(new Result.Success<>(httpResponse.getData()));
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<List<HotelModel>>> call, @NonNull Throwable throwable) {
                Log.d("HotelRemoteDataSource", "onFailure: " + throwable.getMessage());
                callback.onComplete(new Result.Error<>(DataError.NETWORK.INTERNAL_SERVER_ERROR));
            }
        });
    }
}
