package vn.edu.hcmuaf.fit.travie.core.handler.domain;

import static vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant.TOKEN_PREFIX;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.Route;
import vn.edu.hcmuaf.fit.travie.auth.data.model.refreshtoken.RefreshTokenResponse;
import vn.edu.hcmuaf.fit.travie.auth.data.service.AuthService;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.service.AuthManager;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;

public class TokenAuthenticator implements Authenticator, Interceptor {
    private final AuthManager authManager;
    private final Context context;
    private int attemptRefreshToken = 0;

    public TokenAuthenticator(Context context) {
        this.authManager = new AuthManager(context);
        this.context = context;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NonNull Response response) throws IOException {
        String accessToken = authManager.getAccessToken();
        if (accessToken == null) {
            return null;
        }

        int MAX_ATTEMPT_REFRESH_TOKEN = 3;
        if (attemptRefreshToken >= MAX_ATTEMPT_REFRESH_TOKEN) {
            authManager.clearLoggedInUser();
            Intent intent = new Intent();
            intent.setAction("vn.edu.hcmuaf.fit.travie.ACTION_LOGOUT");
            context.sendBroadcast(intent);
            return null;
        }

        if (response.code() == 401) {
            String refreshToken = authManager.getRefreshToken();
            attemptRefreshToken++;
            if (refreshToken == null) {
                return null;
            }

            HttpResponse<RefreshTokenResponse> httpResponse = renewAccessToken(refreshToken);
            if (httpResponse != null && httpResponse.isSuccess()) {
                RefreshTokenResponse refreshTokenResponse = httpResponse.getData();
                authManager.saveAccessToken(refreshTokenResponse.getAccessToken());
                authManager.saveRefreshToken(refreshTokenResponse.getRefreshToken());
                return response.request().newBuilder()
                        .header("Authorization", TOKEN_PREFIX + refreshTokenResponse.getAccessToken())
                        .build();
            }

            return null;
        }

        return response.request()
                .newBuilder()
                .header("Authorization", TOKEN_PREFIX + accessToken)
                .build();
    }

    synchronized private HttpResponse<RefreshTokenResponse> renewAccessToken(String refreshToken) throws IOException {
        AuthService service = RetrofitService.createPublicService(AuthService.class);
        retrofit2.Response<HttpResponse<RefreshTokenResponse>> response = service.refreshToken(refreshToken).execute();

        try (ResponseBody errorBody = response.errorBody()) {
            if (!response.isSuccessful() && errorBody != null) {
                Gson gson = AppUtil.getGson();
                Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                return gson.fromJson(errorBody.charStream(), type);
            }

            if (response.body() == null) {
                return null;
            }

            return response.body();
        }
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Log.d("TokenAuthenticator", "intercept: " + request.url());
        String accessToken = authManager.getAccessToken();
        if (accessToken != null) {
            request = request.newBuilder()
                    .header("Authorization", TOKEN_PREFIX + accessToken)
                    .build();
        }
        return chain.proceed(request);
    }
}
