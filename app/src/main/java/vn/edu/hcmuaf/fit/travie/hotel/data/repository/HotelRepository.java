package vn.edu.hcmuaf.fit.travie.hotel.data.repository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.core.handler.error.DataError;
import vn.edu.hcmuaf.fit.travie.core.handler.ResultCallback;
import vn.edu.hcmuaf.fit.travie.hotel.data.datasource.network.HotelRemoteDataSource;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.HotelModel;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HotelRepository {
    private final HotelRemoteDataSource hotelRemoteDataSource;

    public void getHotelList(ResultCallback<List<HotelModel>, DataError> callback) {
        hotelRemoteDataSource.getHotelList(callback);
    }

    public void getHotel(int id) {
    }
}
