package com.poc.speedtracker.presentation.moving;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.LocationServices;
import com.poc.speedtracker.data.LocationModel;
import com.poc.speedtracker.domain.services.LocationService;
import com.poc.speedtracker.domain.services.impl.LocationServiceImpl;

public class MovingViewModel extends AndroidViewModel {
    private final LiveData<LocationModel> currentSpeedObservable;

    public MovingViewModel(Application application) {
        super(application);
        // If any transformation is needed, this can be simply done by Transformations class ...
        LocationService locationService = new LocationServiceImpl();
        currentSpeedObservable = new LocationLiveData(application);
    }

    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<LocationModel> getCurrentSpeed() {
        return currentSpeedObservable;
    }
}