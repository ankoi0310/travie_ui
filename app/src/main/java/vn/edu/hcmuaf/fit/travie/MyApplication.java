package vn.edu.hcmuaf.fit.travie;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
