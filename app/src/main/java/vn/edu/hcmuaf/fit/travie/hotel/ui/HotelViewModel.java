package vn.edu.hcmuaf.fit.travie.hotel.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;
import vn.edu.hcmuaf.fit.travie.hotel.data.service.HotelService;

public class HotelViewModel extends ViewModel {
    private final MutableLiveData<HotelListResult> nearByHotelList = new MutableLiveData<>();
    private final MutableLiveData<HotelListResult> popularHotelList = new MutableLiveData<>();
    private final MutableLiveData<HotelListResult> exploreHotelList = new MutableLiveData<>();
    private final MutableLiveData<HotelResult> hotel = new MutableLiveData<>();

    private final HotelService hotelService;

    public HotelViewModel(Context context) {
        this.hotelService = RetrofitService.createPublicService(context, HotelService.class);
    }

    public LiveData<HotelListResult> getNearByHotelList() {
        return nearByHotelList;
    }

    public LiveData<HotelListResult> getPopularHotelList() {
        return popularHotelList;
    }

    public LiveData<HotelListResult> getExploreHotelList() {
        return exploreHotelList;
    }

    public LiveData<HotelResult> getHotel() {
        return hotel;
    }

    public void fetchNearByHotelList(String location) {
        hotelService.getNearByHotelList(location).enqueue(new Callback<HttpResponse<ArrayList<Hotel>>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<ArrayList<Hotel>>> call, @NonNull Response<HttpResponse<ArrayList<Hotel>>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        exploreHotelList.postValue(new HotelListResult(null, httpResponse.getMessage()));
                        return;
                    }

                    if (response.body() == null) {
                        exploreHotelList.postValue(new HotelListResult(null, "Something went wrong"));
                        return;
                    }

                    HttpResponse<ArrayList<Hotel>> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        exploreHotelList.postValue(new HotelListResult(null, httpResponse.getMessage()));
                        return;
                    }

                    nearByHotelList.postValue(new HotelListResult(httpResponse.getData(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<ArrayList<Hotel>>> call, @NonNull Throwable t) {
                exploreHotelList.postValue(new HotelListResult(null, "Something went wrong"));
            }
        });
    }

    public void fetchPopularHotelList() {
        hotelService.getPopularHotelList().enqueue(new Callback<HttpResponse<ArrayList<Hotel>>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<ArrayList<Hotel>>> call, @NonNull Response<HttpResponse<ArrayList<Hotel>>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        exploreHotelList.postValue(new HotelListResult(null, httpResponse.getMessage()));
                        return;
                    }

                    if (response.body() == null) {
                        exploreHotelList.postValue(new HotelListResult(null, "Something went wrong"));
                        return;
                    }

                    HttpResponse<ArrayList<Hotel>> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        exploreHotelList.postValue(new HotelListResult(null, httpResponse.getMessage()));
                        return;
                    }

                    popularHotelList.postValue(new HotelListResult(httpResponse.getData(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<ArrayList<Hotel>>> call, @NonNull Throwable t) {
                exploreHotelList.postValue(new HotelListResult(null, "Something went wrong"));
            }
        });
    }

    public void fetchExploreHotelList() {
        hotelService.getExploreHotelList().enqueue(new Callback<HttpResponse<ArrayList<Hotel>>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<ArrayList<Hotel>>> call, @NonNull Response<HttpResponse<ArrayList<Hotel>>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        exploreHotelList.postValue(new HotelListResult(null, httpResponse.getMessage()));
                        return;
                    }

                    if (response.body() == null) {
                        exploreHotelList.postValue(new HotelListResult(null, "Something went wrong"));
                        return;
                    }

                    HttpResponse<ArrayList<Hotel>> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        exploreHotelList.postValue(new HotelListResult(null, httpResponse.getMessage()));
                        return;
                    }

                    exploreHotelList.postValue(new HotelListResult(httpResponse.getData(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<ArrayList<Hotel>>> call, @NonNull Throwable t) {
                exploreHotelList.postValue(new HotelListResult(null, "Something went wrong"));
            }
        });
    }

    public void getHotelById(long id) {
        hotelService.getHotelById(id).enqueue(new Callback<HttpResponse<Hotel>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<Hotel>> call, @NonNull Response<HttpResponse<Hotel>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        hotel.postValue(new HotelResult(null, httpResponse.getMessage()));
                        return;
                    }

                    if (response.body() == null) {
                        hotel.postValue(new HotelResult(null, "Something went wrong"));
                        return;
                    }

                    HttpResponse<Hotel> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        hotel.postValue(new HotelResult(null, httpResponse.getMessage()));
                        return;
                    }

                    hotel.postValue(new HotelResult(httpResponse.getData(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<Hotel>> call, @NonNull Throwable t) {
                hotel.postValue(new HotelResult(null, "Something went wrong"));
            }
        });
    }
}
