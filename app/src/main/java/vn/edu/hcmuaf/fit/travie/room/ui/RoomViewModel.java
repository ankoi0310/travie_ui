package vn.edu.hcmuaf.fit.travie.room.ui;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.Room;
import vn.edu.hcmuaf.fit.travie.room.data.service.RoomService;

@Singleton
public class RoomViewModel extends ViewModel {
    private final MutableLiveData<RoomListResult> roomListResult = new MutableLiveData<>();
    private final MutableLiveData<RoomResult> roomResult = new MutableLiveData<>();

    private final RoomService roomService;

    @Inject
    public RoomViewModel(RoomService roomService) {
        this.roomService = roomService;
    }

    public LiveData<RoomListResult> getRoomListResult() {
        return roomListResult;
    }

    public LiveData<RoomResult> getRoomResult() {
        return roomResult;
    }

    public void search(Long hotelId, Boolean applyFlashSale) {
        roomService.search(hotelId, applyFlashSale).enqueue(new retrofit2.Callback<HttpResponse<ArrayList<Room>>>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<HttpResponse<ArrayList<Room>>> call, @NonNull retrofit2.Response<HttpResponse<ArrayList<Room>>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        roomListResult.postValue(new RoomListResult(null, httpResponse.getMessage()));
                        return;
                    }

                    if (response.body() == null) {
                        roomListResult.postValue(new RoomListResult(null, "Something went wrong"));
                        return;
                    }

                    HttpResponse<ArrayList<Room>> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        roomListResult.postValue(new RoomListResult(null, httpResponse.getMessage()));
                        return;
                    }

                    roomListResult.postValue(new RoomListResult(httpResponse.getData(), null));
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<HttpResponse<ArrayList<Room>>> call, @NonNull Throwable t) {
                Log.d("RoomViewModel", "onFailure: " + t.getMessage());
                roomListResult.postValue(new RoomListResult(null, "Something went wrong"));
            }
        });
    }

    public void getRoomById(int id) {
        roomService.getRoomById(id).enqueue(new retrofit2.Callback<HttpResponse<Room>>() {
            @Override
            public void onResponse(@NonNull retrofit2.Call<HttpResponse<Room>> call, @NonNull retrofit2.Response<HttpResponse<Room>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        roomResult.postValue(new RoomResult(httpResponse.getMessage()));
                    }

                    if (response.body() == null) {
                        roomResult.postValue(new RoomResult("Something went wrong"));
                    }

                    HttpResponse<Room> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        roomResult.postValue(new RoomResult(httpResponse.getMessage()));
                    }

                    roomResult.postValue(new RoomResult(httpResponse.getData()));
                }
            }

            @Override
            public void onFailure(@NonNull retrofit2.Call<HttpResponse<Room>> call, @NonNull Throwable t) {
                roomResult.postValue(new RoomResult("Something went wrong"));
            }
        });
    }
}
