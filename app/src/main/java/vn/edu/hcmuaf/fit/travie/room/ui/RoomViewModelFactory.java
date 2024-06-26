package vn.edu.hcmuaf.fit.travie.room.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RoomViewModel.class)) {
            return (T) new RoomViewModel();
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
