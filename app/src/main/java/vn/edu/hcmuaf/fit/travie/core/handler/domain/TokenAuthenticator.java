package vn.edu.hcmuaf.fit.travie.core.handler.domain;

import static vn.edu.hcmuaf.fit.travie.core.shared.constant.AppConstant.TOKEN_PREFIX;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import vn.edu.hcmuaf.fit.travie.auth.model.RefreshTokenResponse;
import vn.edu.hcmuaf.fit.travie.auth.service.AuthService;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.service.TokenManager;

@Singleton
public class TokenAuthenticator implements Authenticator, Interceptor {
    private final TokenManager tokenManager;
    private final Context context;

    @Inject
    public TokenAuthenticator(Context context) {
        this.context = context;
        this.tokenManager = new TokenManager(context);
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, @NonNull Response response) throws IOException {
        String accessToken = tokenManager.getAccessToken();
        Log.d("TokenAuthenticator", "accessToken: " + accessToken);
        if (accessToken == null) {
            return null;
        }

        if (response.code() == 401) {
            String refreshToken = tokenManager.getRefreshToken();
            Log.d("TokenAuthenticator", "refreshToken: " + refreshToken);
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

        return response.request().newBuilder()
                .header("Authorization", TOKEN_PREFIX + accessToken)
                .build();
    }

    private HttpResponse<RefreshTokenResponse> renewAccessToken(String refreshToken) throws IOException {
        AuthService service = RetrofitService.createService(context, AuthService.class);
        String authorization = TOKEN_PREFIX + refreshToken;
        Log.d("TokenAuthenticator", "authorization: " + authorization);
        return service.refreshToken(authorization).execute().body();
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        String accessToken = tokenManager.getAccessToken();
        Log.d("TokenAuthenticator", "accessToken: " + accessToken);
        if (accessToken != null) {
            request = request.newBuilder()
                    .header("Authorization", TOKEN_PREFIX + accessToken)
                    .build();
        }
        return chain.proceed(request);
    }
}
