package vn.edu.hcmuaf.fit.travie.core.handler.domain;

import static vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant.TOKEN_PREFIX;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Retrofit;
import vn.edu.hcmuaf.fit.travie.auth.model.RefreshTokenRequest;
import vn.edu.hcmuaf.fit.travie.auth.model.RefreshTokenResponse;
import vn.edu.hcmuaf.fit.travie.auth.service.AuthService;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.service.TokenManager;

public class TokenAuthenticator implements Authenticator, Interceptor {
    private final TokenManager tokenManager;
    private int attemptRefreshToken = 0;

    public TokenAuthenticator(Context context) {
        this.tokenManager = new TokenManager(context);
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NonNull Response response) throws IOException {
        String accessToken = tokenManager.getAccessToken();
        if (accessToken == null) {
            return null;
        }

        if (attemptRefreshToken >= 3) {
            return null;
        }

        if (response.code() == 401) {
            String refreshToken = tokenManager.getRefreshToken();
            attemptRefreshToken++;
            if (refreshToken == null) {
                return null;
            }

            HttpResponse<RefreshTokenResponse> httpResponse = renewAccessToken(refreshToken);
            if (httpResponse.isSuccess()) {
                RefreshTokenResponse refreshTokenResponse = httpResponse.getData();
                tokenManager.saveAccessToken(refreshTokenResponse.getAccessToken());
                tokenManager.saveRefreshToken(refreshTokenResponse.getRefreshToken());
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
        OkHttpClient client = RetrofitService.clientBuilder.build();
        Retrofit retrofit = RetrofitService.builder.client(client).build();
        AuthService service = retrofit.create(AuthService.class);
        RefreshTokenRequest request = new RefreshTokenRequest(refreshToken);
        retrofit2.Response<HttpResponse<RefreshTokenResponse>> response = service.refreshToken(request).execute();
        return response.body();
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Log.d("TokenAuthenticator", "intercept: " + request.url());
        String accessToken = tokenManager.getAccessToken();
        if (accessToken != null) {
            request = request.newBuilder()
                    .header("Authorization", TOKEN_PREFIX + accessToken)
                    .build();
        }
        return chain.proceed(request);
    }
}
