package vn.edu.hcmuaf.fit.travie.hotel.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;

import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.hotel.data.service.HotelService;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HotelViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HotelViewModel.class)) {
            return (T) new HotelViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
