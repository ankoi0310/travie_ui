package vn.edu.hcmuaf.fit.travie.home.data.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.paging.Page;
import vn.edu.hcmuaf.fit.travie.home.data.model.Banner;

public interface BannerService {
    @GET("banner")
    Call<HttpResponse<Page<Banner>>> getBanners(@Query("page") int page, @Query("size") int size);
}
