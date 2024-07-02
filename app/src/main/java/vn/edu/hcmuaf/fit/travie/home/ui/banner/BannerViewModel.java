package vn.edu.hcmuaf.fit.travie.home.ui.banner;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.paging.Page;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.home.data.model.Banner;
import vn.edu.hcmuaf.fit.travie.home.data.service.BannerService;

@Singleton
public class BannerViewModel extends ViewModel {
    private final MutableLiveData<BannerListResult> bannerListResult = new MutableLiveData<>();

    private final BannerService bannerService;

    @Inject
    public BannerViewModel() {
        bannerService = RetrofitService.createPublicService(BannerService.class);
    }

    public LiveData<BannerListResult> getBannerListResult() {
        return bannerListResult;
    }

    public void loadBanners(int page, int size) {
        bannerService.getBanners(page, size).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<Page<Banner>>> call, @NonNull Response<HttpResponse<Page<Banner>>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {
                        }.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        bannerListResult.postValue(new BannerListResult(null, httpResponse.getMessage()));
                        return;
                    }

                    if (response.body() == null) {
                        bannerListResult.postValue(new BannerListResult(null, "Something went wrong"));
                        return;
                    }

                    HttpResponse<Page<Banner>> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        bannerListResult.postValue(new BannerListResult(null, httpResponse.getMessage()));
                        return;
                    }

                    bannerListResult.postValue(new BannerListResult(httpResponse.getData().getContent(), null));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<Page<Banner>>> call, @NonNull Throwable throwable) {
                bannerListResult.postValue(new BannerListResult(null, "Something went wrong"));
            }
        });
    }
}
