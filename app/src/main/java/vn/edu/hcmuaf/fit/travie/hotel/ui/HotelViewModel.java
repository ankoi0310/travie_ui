package vn.edu.hcmuaf.fit.travie.hotel.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Hotel;
import vn.edu.hcmuaf.fit.travie.hotel.data.service.HotelService;
import vn.edu.hcmuaf.fit.travie.hotel.ui.explore.HotelResult;

@Singleton
public class HotelViewModel extends ViewModel {
    private final MutableLiveData<HotelResult> nearByHotelResult = new MutableLiveData<>();
    private final MutableLiveData<HotelResult> popularHotelResult = new MutableLiveData<>();
    private final MutableLiveData<HotelResult> exploreHotelResult = new MutableLiveData<>();

    private final HotelService hotelService;

    @Inject
    public HotelViewModel(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    public LiveData<HotelResult> getNearByHotelResult() {
        return nearByHotelResult;
    }

    public LiveData<HotelResult> getPopularHotelResult() {
        return popularHotelResult;
    }

    public LiveData<HotelResult> getExploreHotelResult() {
        return exploreHotelResult;
    }

    public void fetchNearByHotelList() {
        hotelService.getNearByHotelList().enqueue(new Callback<HttpResponse<List<Hotel>>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<List<Hotel>>> call, @NonNull Response<HttpResponse<List<Hotel>>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        exploreHotelResult.setValue(new HotelResult(httpResponse.getMessage()));
                    }

                    if (response.body() == null) {
                        exploreHotelResult.setValue(new HotelResult("Something went wrong"));
                    }

                    HttpResponse<List<Hotel>> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        exploreHotelResult.setValue(new HotelResult(httpResponse.getMessage()));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<List<Hotel>>> call, @NonNull Throwable t) {
                exploreHotelResult.setValue(new HotelResult("Something went wrong"));
            }
        });
    }

    public void fetchPopularHotelList() {
        hotelService.getPopularHotelList().enqueue(new Callback<HttpResponse<List<Hotel>>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<List<Hotel>>> call, @NonNull Response<HttpResponse<List<Hotel>>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        exploreHotelResult.setValue(new HotelResult(httpResponse.getMessage()));
                    }

                    if (response.body() == null) {
                        exploreHotelResult.setValue(new HotelResult("Something went wrong"));
                    }

                    HttpResponse<List<Hotel>> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        exploreHotelResult.setValue(new HotelResult(httpResponse.getMessage()));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<List<Hotel>>> call, @NonNull Throwable t) {
                exploreHotelResult.setValue(new HotelResult("Something went wrong"));
            }
        });
    }

    public void fetchExploreHotelList() {
        hotelService.getExploreHotelList().enqueue(new Callback<HttpResponse<List<Hotel>>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<List<Hotel>>> call, @NonNull Response<HttpResponse<List<Hotel>>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        exploreHotelResult.setValue(new HotelResult(httpResponse.getMessage()));
                    }

                    if (response.body() == null) {
                        exploreHotelResult.setValue(new HotelResult("Something went wrong"));
                    }

                    HttpResponse<List<Hotel>> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        exploreHotelResult.setValue(new HotelResult(httpResponse.getMessage()));
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<List<Hotel>>> call, @NonNull Throwable t) {
                exploreHotelResult.setValue(new HotelResult("Something went wrong"));
            }
        });
    }
}
