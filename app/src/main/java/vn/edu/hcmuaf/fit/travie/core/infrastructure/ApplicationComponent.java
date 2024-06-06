package vn.edu.hcmuaf.fit.travie.core.infrastructure;

import javax.inject.Singleton;

import dagger.Component;
import vn.edu.hcmuaf.fit.travie.auth.ui.login.LoginActivity;

@Singleton
@Component(modules = {NetworkModule.class})
public interface ApplicationComponent {
    void inject(LoginActivity loginActivity);
}
