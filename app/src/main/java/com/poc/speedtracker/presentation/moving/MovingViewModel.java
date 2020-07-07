package com.poc.speedtracker.presentation.moving;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.LocationServices;
import com.poc.speedtracker.data.LocationModel;
import com.poc.speedtracker.data.LocationRepository;
import com.poc.speedtracker.domain.services.LocationService;
import com.poc.speedtracker.domain.services.impl.LocationServiceImpl;

public class MovingViewModel extends AndroidViewModel {
    private final LiveData<LocationModel> currentSpeedObservable;
    private LocationService locationService;

    public MovingViewModel(Application application) {
        super(application);
        // If any transformation is needed, this can be simply done by Transformations class ...
        locationService = new LocationServiceImpl(new LocationRepository(application));
        currentSpeedObservable = locationService.getLocationData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        locationService.stopLocation();
    }

    public LiveData<Float> getCurrentSpeed() {
        return Transformations.map(currentSpeedObservable, new Function<LocationModel, Float>() {
            @Override
            public Float apply(LocationModel input) {
                return round(input.speed, 1);
            }
        });
    }

    private float round(float value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (float) Math.round(value * scale) / scale;
    }
}