package vn.edu.hcmuaf.fit.travie.auth.ui.login;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;

import vn.edu.hcmuaf.fit.travie.auth.data.datasource.network.AuthenticationRemoteDataSource;
import vn.edu.hcmuaf.fit.travie.auth.data.repository.AuthenticationRepositoryImpl;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class LoginViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(new AuthenticationRepositoryImpl(new AuthenticationRemoteDataSource(RetrofitService.getInstance())));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}