package vn.edu.hcmuaf.fit.travie.room.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.RequiredArgsConstructor;
import vn.edu.hcmuaf.fit.travie.room.data.service.RoomService;

@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class RoomViewModelFactory implements ViewModelProvider.Factory {
    private final RoomService roomService;

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RoomViewModel.class)) {
            return (T) new RoomViewModel(roomService);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
