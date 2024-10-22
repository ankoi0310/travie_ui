package vn.edu.hcmuaf.fit.travie.core.service;

import android.content.Context;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import vn.edu.hcmuaf.fit.travie.BuildConfig;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.TokenAuthenticator;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.DateTimeUtil;

public class RetrofitService {
    private static final GsonBuilder gsonBuilder = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json
                    , typeOfT, context) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeUtil.getDateTimeFormatter()))
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src
                    , typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeUtil.getDateTimeFormatter())))
            .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json
                    , typeOfT, context) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString(), DateTimeUtil.getDateTimeFormatter("dd-MM-yyyy")))
            .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (src
                    , typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeUtil.getDateTimeFormatter("dd-MM-yyyy"))))
            .registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json
                    , typeOfT, context) -> LocalTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeUtil.getDateTimeFormatter("HH:mm")))
            .registerTypeAdapter(LocalTime.class, (JsonSerializer<LocalTime>) (src
                    , typeOfSrc, context) -> new JsonPrimitive(src.format(DateTimeUtil.getDateTimeFormatter("HH:mm"))));
    public static final Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()));

    public static final OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS);

    public static <S> S createPublicService(@NotNull Class<S> serviceClass) {
        OkHttpClient client = clientBuilder.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createPrivateService(Context context, @NotNull Class<S> serviceClass) {
        TokenAuthenticator tokenAuthenticator = new TokenAuthenticator(context);
        OkHttpClient client = clientBuilder
                .addInterceptor(tokenAuthenticator)
                .authenticator(tokenAuthenticator)
                .build();

        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }
}
