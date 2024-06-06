package vn.edu.hcmuaf.fit.travie.core.service;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.hcmuaf.fit.travie.BuildConfig;

public class RetrofitService {
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
//            .addInterceptor(new Interceptor() {
//                @NonNull
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request request = chain.request().newBuilder()
//                            .addHeader("Authorization", "Bearer " + BuildConfig.API_KEY)
//                            .build();
//                    return chain.proceed(request);
//                }
//            })
            .build();

    private Retrofit retrofit = null;

    public RetrofitService() {
        initRetrofit();
    }

    public void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
