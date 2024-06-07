package vn.edu.hcmuaf.fit.travie.hotel.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.hotel.data.repository.HotelRepository;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HotelViewModelFactory implements ViewModelProvider.Factory {
    private final HotelRepository hotelRepository;

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HotelViewModel.class)) {
            return (T) new HotelViewModel(hotelRepository);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
