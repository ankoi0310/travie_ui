package vn.edu.hcmuaf.fit.travie.core.service;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.hcmuaf.fit.travie.BuildConfig;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.DateTimeUtil;

@Singleton
public class RetrofitService {
    private static final GsonBuilder gsonBuilder = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json
                    , typeOfT, context) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeUtil.getSimpleDateFormat()))
            .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json
                    , typeOfT, context) -> LocalTime.parse(json.getAsJsonPrimitive().getAsString()));

    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS);

    private static final Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()));

    private static Retrofit retrofit = builder.build();

    private static RetrofitService _instance;

    public static RetrofitService getInstance() {
        if (_instance == null) {
            _instance = new RetrofitService();
        }
        return _instance;
    }

    private RetrofitService() {
    }

    public <S> S createService(Class<S> serviceClass, final String token ) {
        if ( token != null ) {
            httpClient.interceptors().clear();
            httpClient.addInterceptor( chain -> {
                Request original = chain.request();
                Request request = original.newBuilder()
                        .header("Authorization", token)
                        .build();
                return chain.proceed(request);
            });
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }
}
