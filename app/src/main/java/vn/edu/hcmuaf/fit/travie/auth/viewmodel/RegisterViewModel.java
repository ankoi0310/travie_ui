package vn.edu.hcmuaf.fit.travie.auth.viewmodel;

import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.time.LocalDate;

import lombok.Getter;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.auth.data.model.register.RegisterFormState;
import vn.edu.hcmuaf.fit.travie.auth.data.model.register.RegisterRequest;
import vn.edu.hcmuaf.fit.travie.auth.data.model.register.RegisterResponse;
import vn.edu.hcmuaf.fit.travie.auth.data.model.register.RegisterResult;
import vn.edu.hcmuaf.fit.travie.auth.data.service.AuthService;
import vn.edu.hcmuaf.fit.travie.core.handler.domain.HttpResponse;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.core.shared.enums.Gender;
import vn.edu.hcmuaf.fit.travie.core.shared.utils.AppUtil;

public class RegisterViewModel extends ViewModel {
    @Getter
    private final MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>(RegisterFormState.getInstance());
    private final AuthService authService;

    public RegisterViewModel() {
        this.authService = RetrofitService.createPublicService(AuthService.class);
    }
}
