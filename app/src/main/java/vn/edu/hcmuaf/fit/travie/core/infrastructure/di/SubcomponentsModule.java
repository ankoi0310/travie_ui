package vn.edu.hcmuaf.fit.travie.core.infrastructure.di;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vn.edu.hcmuaf.fit.travie.core.common.di.MainComponent;

@Module(subcomponents = {MainComponent.class})
public class SubcomponentsModule {
    private final Application application;

    public SubcomponentsModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return application;
    }
}
