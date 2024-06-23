package vn.edu.hcmuaf.fit.travie.user.ui.changepassword;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.user.service.UserService;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class ChangePasswordViewModelFactory implements ViewModelProvider.Factory {
    private final UserService userService;

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ChangePasswordViewModel.class)) {
            return (T) new ChangePasswordViewModel(userService);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
