package vn.edu.hcmuaf.fit.travie.auth.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.edu.hcmuaf.fit.travie.auth.data.model.LoginResponse;
import vn.edu.hcmuaf.fit.travie.auth.data.model.LoginRequest;
import vn.edu.hcmuaf.fit.travie.auth.domain.repository.AuthenticationRepository;
import vn.edu.hcmuaf.fit.travie.core.handler.Result;
import vn.edu.hcmuaf.fit.travie.R;
import vn.edu.hcmuaf.fit.travie.core.handler.error.DataError;

@Singleton
public class LoginViewModel extends ViewModel {

    private final MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private final MutableLiveData<Result<LoginResponse, DataError>> loginResult = new MutableLiveData<>();
    private final AuthenticationRepository authenticationRepository;

    @Inject
    LoginViewModel(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<Result<LoginResponse, DataError>> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        LoginRequest loginRequest = new LoginRequest(username, password);
        authenticationRepository.login(loginRequest, loginResult::setValue);
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }
}