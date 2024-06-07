package vn.edu.hcmuaf.fit.travie.auth.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.auth.data.repository.AuthenticationRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class LoginViewModelFactory implements ViewModelProvider.Factory {
    private final AuthenticationRepository authenticationRepository;

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(authenticationRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}