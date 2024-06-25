package vn.edu.hcmuaf.fit.travie.booking.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookingViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BookingViewModel.class)) {
            return (T) new BookingViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
