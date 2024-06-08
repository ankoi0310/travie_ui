package vn.edu.hcmuaf.fit.travie;

import android.app.Application;

import lombok.Getter;
import vn.edu.hcmuaf.fit.travie.core.infrastructure.di.ApplicationComponent;
import vn.edu.hcmuaf.fit.travie.core.infrastructure.di.DaggerApplicationComponent;

@Getter
public class MyApplication extends Application {
    private final ApplicationComponent applicationComponent = DaggerApplicationComponent.builder().build();
}
