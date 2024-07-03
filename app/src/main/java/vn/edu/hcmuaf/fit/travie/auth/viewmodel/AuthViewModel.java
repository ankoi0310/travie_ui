package vn.edu.hcmuaf.fit.travie.auth.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;

import lombok.Getter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.auth.data.model.forgotpassword.ResetPasswordRequest;
import vn.edu.hcmuaf.fit.travie.auth.data.model.login.LoginRequest;
import vn.edu.hcmuaf.fit.travie.auth.data.model.login.LoginResponse;
import vn.edu.hcmuaf.fit.travie.auth.data.model.register.RegisterRequest;
import vn.edu.hcmuaf.fit.travie.auth.data.model.register.RegisterResponse;
import vn.edu.hcmuaf.fit.travie.auth.data.model.register.RegisterResult;
import vn.edu.hcmuaf.fit.travie.auth.data.service.AuthService;
import vn.edu.hcmuaf.fit.travie.auth.ui.login.LoginResult;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;

public class AuthViewModel extends ViewModel {
    @Getter
    private final MutableLiveData<HttpResponse<String>> checkEmailResponse = new MutableLiveData<>();

    @Getter
    private final MutableLiveData<RegisterResult> registerResult = new MutableLiveData<>();

    @Getter
    private final MutableLiveData<HttpResponse<String>> forgotPasswordResponse = new MutableLiveData<>();

    @Getter
    private final MutableLiveData<HttpResponse<String>> resetPasswordResponse = new MutableLiveData<>();

    @Getter
    private final MutableLiveData<HttpResponse<String>> verifyResponse = new MutableLiveData<>();

    @Getter
    private final MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    private final AuthService authService;

    public AuthViewModel() {
        this.authService = RetrofitService.createPublicService(AuthService.class);
    }

    public void checkEmail(String email) {
        authService.checkEmail(email).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<String>> call, @NonNull Response<HttpResponse<String>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {
                        }.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        checkEmailResponse.postValue(httpResponse);
                        return;
                    }

                    if (response.body() == null) {
                        checkEmailResponse.postValue(null);
                        return;
                    }

                    HttpResponse<String> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        checkEmailResponse.postValue(httpResponse);
                        return;
                    }

                    checkEmailResponse.postValue(httpResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<String>> call, @NonNull Throwable t) {
                checkEmailResponse.postValue(null);
            }
        });
    }

    public void register(File avatarFile, RegisterRequest request) {
        MultipartBody.Part avatar = createAvatarPart(avatarFile);
        Gson gson = AppUtil.getGson();
        String json = gson.toJson(request);
        RequestBody requestBody = RequestBody.create(json, MediaType.parse("application/json"));
        authService.register(avatar, requestBody).enqueue(new retrofit2.Callback<>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<RegisterResponse>> call, @NonNull Response<HttpResponse<RegisterResponse>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {
                        }.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        registerResult.postValue(RegisterResult.error(httpResponse.getMessage()));
                        return;
                    }

                    if (response.body() == null) {
                        registerResult.postValue(RegisterResult.error("Something went wrong"));
                        return;
                    }

                    HttpResponse<RegisterResponse> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        registerResult.postValue(RegisterResult.error(httpResponse.getMessage()));
                        return;
                    }

                    registerResult.postValue(RegisterResult.success(httpResponse.getData()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<RegisterResponse>> call, @NonNull Throwable t) {
                registerResult.postValue(RegisterResult.error(t.getMessage()));
            }
        });
    }

    private MultipartBody.Part createAvatarPart(File avatarFile) {
        if (avatarFile == null) {
            return null;
        }

        RequestBody requestFile = RequestBody.create(avatarFile, MediaType.parse("image/*"));
        return MultipartBody.Part.createFormData("avatar", avatarFile.getName(), requestFile);
    }

    public void verify(String code) {
        authService.verify(code).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<String>> call, @NonNull Response<HttpResponse<String>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {
                        }.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        verifyResponse.postValue(httpResponse);
                        return;
                    }

                    if (response.body() == null) {
                        verifyResponse.postValue(null);
                        return;
                    }

                    HttpResponse<String> httpResponse = response.body();
                    verifyResponse.postValue(httpResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<String>> call, @NonNull Throwable t) {
                verifyResponse.postValue(null);
            }
        });
    }

    public void forgotPassword(String email) {
        authService.forgotPassword(email).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<String>> call, @NonNull Response<HttpResponse<String>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {
                        }.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        forgotPasswordResponse.postValue(httpResponse);
                        return;
                    }

                    if (response.body() == null) {
                        forgotPasswordResponse.postValue(null);
                        return;
                    }

                    HttpResponse<String> httpResponse = response.body();
                    forgotPasswordResponse.postValue(httpResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<String>> call, @NonNull Throwable t) {
                forgotPasswordResponse.postValue(null);
            }
        });
    }

    public void resetPassword(String email, String newPassword) {
        ResetPasswordRequest request = new ResetPasswordRequest(email, newPassword);
        authService.resetPassword(request).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<String>> call, @NonNull Response<HttpResponse<String>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {
                        }.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        resetPasswordResponse.postValue(httpResponse);
                        return;
                    }

                    if (response.body() == null) {
                        resetPasswordResponse.postValue(null);
                        return;
                    }

                    HttpResponse<String> httpResponse = response.body();
                    resetPasswordResponse.postValue(httpResponse);
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<String>> call, @NonNull Throwable t) {
                resetPasswordResponse.postValue(null);
            }
        });
    }

    public void login(LoginRequest loginRequest) {
        authService.login(loginRequest).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<LoginResponse>> call, @NonNull Response<HttpResponse<LoginResponse>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {
                        }.getType();
                        HttpResponse<String> httpResponse = gson.fromJson(errorBody.charStream(), type);
                        loginResult.postValue(LoginResult.error(httpResponse.getMessage()));
                        return;
                    }

                    if (response.body() == null) {
                        loginResult.postValue(LoginResult.error("Something went wrong"));
                        return;
                    }

                    HttpResponse<LoginResponse> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        loginResult.postValue(LoginResult.error(httpResponse.getMessage()));
                        return;
                    }

                    loginResult.postValue(LoginResult.success(httpResponse.getData()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<LoginResponse>> call, @NonNull Throwable t) {
                loginResult.postValue(LoginResult.error(t.getMessage()));
            }
        });
    }

    public void loginWithFacebook(String accessToken, MutableLiveData<HttpResponse<LoginResponse>> loginResponse) {
        authService.loginFacebook(accessToken).enqueue(new Callback<>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<LoginResponse>> call, @NonNull Response<HttpResponse<LoginResponse>> response) {
                loginResponse.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<LoginResponse>> call, @NonNull Throwable t) {
//                loginResponse.postValue(new HttpResponse<>(t.getMessage()));
            }
        });
    }
}
