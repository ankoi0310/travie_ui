package vn.edu.hcmuaf.fit.travie;

import android.app.Application;

import lombok.Getter;
import vn.edu.hcmuaf.fit.travie.core.infrastructure.di.ApplicationComponent;
import vn.edu.hcmuaf.fit.travie.core.infrastructure.di.DaggerApplicationComponent;
import vn.edu.hcmuaf.fit.travie.core.infrastructure.di.NetworkModule;

@Getter
public class MyApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(new NetworkModule())
                .build();
    }

}
