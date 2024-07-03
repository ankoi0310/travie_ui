package vn.edu.hcmuaf.fit.travie.user.ui;

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
import vn.edu.hcmuaf.fit.travie.invoice.data.model.Invoice;
import vn.edu.hcmuaf.fit.travie.invoice.ui.InvoiceListResult;
import vn.edu.hcmuaf.fit.travie.user.data.model.UserProfile;
import vn.edu.hcmuaf.fit.travie.user.data.model.UserProfileRequest;
import vn.edu.hcmuaf.fit.travie.user.data.service.UserService;
import vn.edu.hcmuaf.fit.travie.user.ui.profiledetail.ProfileResult;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<InvoiceListResult> invoices = new MutableLiveData<>();
    private final MutableLiveData<ProfileResult> profileResult = new MutableLiveData<>();

    private final UserService userService;

    public UserViewModel(Context context) {
        this.userService = RetrofitService.createPrivateService(context, UserService.class);
    }

    public LiveData<InvoiceListResult> getInvoices() {
        return invoices;
    }

    public LiveData<ProfileResult> getProfileResult() {
        return profileResult;
    }

    public void fetchBookingHistory() {
        userService.getBookingHistory().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<ArrayList<Invoice>>> call, @NonNull Response<HttpResponse<ArrayList<Invoice>>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {
                        }.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        invoices.postValue(new InvoiceListResult(null, httpResponse.getMessage()));
                        return;
                    }

                    if (response.body() == null) {
                        invoices.postValue(new InvoiceListResult(null, "Something went wrong"));
                        return;
                    }

                    HttpResponse<ArrayList<Invoice>> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        invoices.postValue(new InvoiceListResult(null, httpResponse.getMessage()));
                        return;
                    }

                    invoices.postValue(new InvoiceListResult(httpResponse.getData(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<ArrayList<Invoice>>> call, @NonNull Throwable t) {
                invoices.postValue(new InvoiceListResult(null, "Something went wrong"));
            }
        });
    }

    public void getProfile() {
        userService.getProfile().enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<UserProfile>> call, @NonNull Response<HttpResponse<UserProfile>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {
                        }.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        profileResult.postValue(ProfileResult.error(httpResponse.getMessage()));
                        return;
                    }

                    if (response.body() == null) {
                        profileResult.postValue(ProfileResult.error("Something went wrong"));
                        return;
                    }

                    HttpResponse<UserProfile> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        profileResult.postValue(ProfileResult.error(httpResponse.getMessage()));
                        return;
                    }

                    profileResult.postValue(ProfileResult.success(httpResponse.getData()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<UserProfile>> call, @NonNull Throwable t) {
                profileResult.postValue(ProfileResult.error("Something went wrong"));
            }
        });
    }

    public void updateUserProfile(UserProfileRequest request) {
        userService.updateProfile(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<UserProfile>> call, @NonNull Response<HttpResponse<UserProfile>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {
                        }.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        profileResult.postValue(ProfileResult.error(httpResponse.getMessage()));
                        return;
                    }

                    if (response.body() == null) {
                        profileResult.postValue(ProfileResult.error("Something went wrong"));
                        return;
                    }

                    HttpResponse<UserProfile> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        profileResult.postValue(ProfileResult.error(httpResponse.getMessage()));
                        return;
                    }

                    profileResult.postValue(ProfileResult.success(httpResponse.getData()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<UserProfile>> call, @NonNull Throwable t) {
                profileResult.postValue(ProfileResult.error("Something went wrong"));
            }
        });
    }
}
