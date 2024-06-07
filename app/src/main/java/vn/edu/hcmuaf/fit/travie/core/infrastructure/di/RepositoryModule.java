package vn.edu.hcmuaf.fit.travie.core.infrastructure.di;

import dagger.Module;
import dagger.Provides;
import vn.edu.hcmuaf.fit.travie.auth.data.datasource.network.AuthenticationRemoteDataSource;
import vn.edu.hcmuaf.fit.travie.auth.data.repository.AuthenticationRepository;
import vn.edu.hcmuaf.fit.travie.core.service.RetrofitService;
import vn.edu.hcmuaf.fit.travie.hotel.data.datasource.network.HotelRemoteDataSource;
import vn.edu.hcmuaf.fit.travie.hotel.data.repository.HotelRepository;

@Module
public class RepositoryModule {
    @Provides
    public AuthenticationRepository provideAuthenticationRepository() {
        return new AuthenticationRepository(new AuthenticationRemoteDataSource(RetrofitService.getInstance()));
    }

    @Provides
    public HotelRepository provideHotelRepository() {
        return new HotelRepository(new HotelRemoteDataSource(RetrofitService.getInstance()));
    }
}
