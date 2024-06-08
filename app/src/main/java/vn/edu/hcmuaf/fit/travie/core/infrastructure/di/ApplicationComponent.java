package vn.edu.hcmuaf.fit.travie.core.infrastructure.di;

import javax.inject.Singleton;

import dagger.Component;
import vn.edu.hcmuaf.fit.travie.auth.activity.login.LoginActivity;
import vn.edu.hcmuaf.fit.travie.core.common.di.MainComponent;

@Singleton
@Component(modules = {SubcomponentsModule.class})
public interface ApplicationComponent {
    void inject(LoginActivity loginActivity);

    MainComponent.Factory mainComponent();
}
