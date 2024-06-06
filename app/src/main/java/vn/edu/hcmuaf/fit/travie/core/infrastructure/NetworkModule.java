package vn.edu.hcmuaf.fit.travie.core.infrastructure;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import vn.edu.hcmuaf.fit.travie.auth.data.datasource.network.AuthenticationRemoteDataSource;
import vn.edu.hcmuaf.fit.travie.auth.data.repository.AuthenticationRepositoryImpl;
import vn.edu.hcmuaf.fit.travie.auth.data.service.AuthenticationService;
import vn.edu.hcmuaf.fit.travie.auth.domain.repository.AuthenticationRepository;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;

@Module
public class NetworkModule {

    @Provides
    @Singleton
    public AuthenticationService provideAuthenticationService(String token) {
        return RetrofitService.getInstance().createService(AuthenticationService.class, token);
    }

    @Provides
    @Singleton
    public AuthenticationRepository provideAuthenticationRepository() {
        return new AuthenticationRepositoryImpl(new AuthenticationRemoteDataSource(RetrofitService.getInstance()));
    }
}
