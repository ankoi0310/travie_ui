package vn.edu.hcmuaf.fit.travie.user.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.user.model.UserProfile;
import vn.edu.hcmuaf.fit.travie.user.model.UserProfileRequest;
import vn.edu.hcmuaf.fit.travie.user.service.UserService;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<ProfileResult> profile = new MutableLiveData<>();
    private final UserService userService;

    public UserViewModel(Context context) {
        this.userService = RetrofitService.createPrivateService(context, UserService.class);
    }

    public LiveData<ProfileResult> getProfile() {
        return profile;
    }

    public void updateProfile(UserProfileRequest request) {
        userService.updateProfile(request).enqueue(new Callback<HttpResponse<UserProfile>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<UserProfile>> call, @NonNull Response<HttpResponse<UserProfile>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        profile.postValue(new ProfileResult(null, httpResponse.getMessage()));
                        return;
                    }

                    if (response.body() == null) {
                        profile.postValue(new ProfileResult(null, "Something went wrong"));
                        return;
                    }

                    HttpResponse<UserProfile> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        profile.postValue(new ProfileResult(null, httpResponse.getMessage()));
                        return;
                    }

                    profile.postValue(new ProfileResult(httpResponse.getData(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<UserProfile>> call,@NonNull Throwable t) {
                profile.postValue(new ProfileResult(null, "Something went wrong"));
            }
        });
    }
}
