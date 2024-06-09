package vn.edu.hcmuaf.fit.travie.user.ui.changepassword;

import static vn.edu.hcmuaf.fit.travie.core.shared.constant.FormState.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;
import vn.edu.hcmuaf.fit.travie.user.model.ChangePasswordRequest;
import vn.edu.hcmuaf.fit.travie.user.service.UserService;

@Singleton
public class ChangePasswordViewModel extends ViewModel {
    private final MutableLiveData<ChangePasswordFormState> changePasswordFormState = new MutableLiveData<>();
    private final MutableLiveData<ChangePasswordResult> changePasswordResult = new MutableLiveData<>();

    private final UserService userService;

    @Inject
    ChangePasswordViewModel(UserService userService) {
        this.userService = userService;
    }


    LiveData<ChangePasswordFormState> getChangePasswordFormState() {
        return changePasswordFormState;
    }

    LiveData<ChangePasswordResult> getChangePasswordResult() {
        return changePasswordResult;
    }

    public void changePassword(String currentPassword, String newPassword) {
        userService.changePassword(new ChangePasswordRequest(currentPassword, newPassword)).enqueue(new Callback<HttpResponse<String>>() {
            @Override
            public void onResponse(@NonNull Call<HttpResponse<String>> call, @NonNull Response<HttpResponse<String>> response) {
                try (ResponseBody errorBody = response.errorBody()) {
                    if (!response.isSuccessful() && errorBody != null) {
                        Gson gson = AppUtil.getGson();
                        Type type = new TypeToken<HttpResponse<String>>() {}.getType();
                        HttpResponse<String> errorResponse = gson.fromJson(errorBody.charStream(), type);
                        changePasswordResult.setValue(new ChangePasswordResult(errorResponse.getMessage(), false));
                    }

                    if (response.body() == null) {
                        changePasswordResult.setValue(new ChangePasswordResult("Something went wrong", false));
                    }

                    HttpResponse<String> httpResponse = response.body();
                    if (!httpResponse.isSuccess()) {
                        changePasswordResult.setValue(new ChangePasswordResult(httpResponse.getMessage(), false));
                    }

                    changePasswordResult.setValue(new ChangePasswordResult(response.body().getData(), true));
                }
            }

            @Override
            public void onFailure(@NonNull Call<HttpResponse<String>> call, @NonNull Throwable t) {
                changePasswordResult.setValue(new ChangePasswordResult(t.getMessage(), false));
            }
        });
    }

    public void changePaswordDataChanged(@Nullable String currentPassword, @Nullable String newPassword, @Nullable String confirmPassword) {
        if (!isCurrentPasswordValid(currentPassword)) {
            changePasswordFormState.setValue(new ChangePasswordFormState(CURRENT_PASSWORD_IS_REQUIRED, null, null));
        } else if (!isNewPasswordValid(newPassword)) {
            changePasswordFormState.setValue(new ChangePasswordFormState(null, NEW_PASSWORD_IS_REQUIRED, null));
        } else if (!isConfirmPasswordValid(newPassword, confirmPassword)) {
            changePasswordFormState.setValue(new ChangePasswordFormState(null, null,
                    CONFIRM_PASSWORD_NOT_MATCH));
        } else if (currentPassword.equals(newPassword)) {
            changePasswordFormState.setValue(new ChangePasswordFormState(null,
                    NEW_PASSWORD_MUST_BE_DIFFERENT, null));
        } else {
            changePasswordFormState.setValue(new ChangePasswordFormState(true));
        }
    }

    private boolean isCurrentPasswordValid(String currentPassword) {
        return currentPassword != null && !currentPassword.trim().isEmpty();
    }

    private boolean isNewPasswordValid(String newPassword) {
        return newPassword != null && !newPassword.trim().isEmpty();
    }

    private boolean isConfirmPasswordValid(String newPassword, String confirmPassword) {
        return confirmPassword != null && confirmPassword.equals(newPassword);
    }
}
