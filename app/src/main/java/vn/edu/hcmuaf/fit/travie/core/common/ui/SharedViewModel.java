package vn.edu.hcmuaf.fit.travie.core.common.ui;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Location> lastLocation = new MutableLiveData<>();

    public void setLastLocation(Location location) {
        lastLocation.setValue(location);
    }

    public LiveData<Location> getLastLocation() {
        return lastLocation;
    }
}
