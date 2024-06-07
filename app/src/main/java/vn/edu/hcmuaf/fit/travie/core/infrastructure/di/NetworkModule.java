package vn.edu.hcmuaf.fit.travie.core.infrastructure.di;

import dagger.Module;
import dagger.Provides;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;

@Module
public class NetworkModule {

    @Provides
    public RetrofitService provideRetrofitService() {
        return RetrofitService.getInstance();
    }
}
