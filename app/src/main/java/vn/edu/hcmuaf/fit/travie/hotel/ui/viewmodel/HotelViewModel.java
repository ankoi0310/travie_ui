package vn.edu.hcmuaf.fit.travie.hotel.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import vn.edu.hcmuaf.fit.travie.core.handler.Result;
import vn.edu.hcmuaf.fit.travie.core.handler.error.DataError;
import vn.edu.hcmuaf.fit.travie.hotel.data.model.HotelModel;
import vn.edu.hcmuaf.fit.travie.hotel.data.repository.HotelRepository;

@Singleton
public class HotelViewModel extends ViewModel {
    private final MutableLiveData<Result<List<HotelModel>, DataError>> nearByHotelResult =
            new MutableLiveData<>();
    private final HotelRepository hotelRepository;

    @Inject
    public HotelViewModel(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public LiveData<Result<List<HotelModel>, DataError>> getNearByHotelResult() {
        return nearByHotelResult;
    }

    public void searchNearByHotelList() {
        hotelRepository.getHotelList(nearByHotelResult::setValue);
    }
}
