package vn.edu.hcmuaf.fit.travie.core.infrastructure.di;

import javax.inject.Singleton;

import dagger.Component;
import vn.edu.hcmuaf.fit.travie.auth.ui.login.LoginActivity;
import vn.edu.hcmuaf.fit.travie.core.common.di.MainComponent;

@Singleton
@Component(modules = {NetworkModule.class, RepositoryModule.class, SubcomponentsModule.class})
public interface ApplicationComponent {
    void inject(LoginActivity loginActivity);

    MainComponent.Factory mainComponent();
}
