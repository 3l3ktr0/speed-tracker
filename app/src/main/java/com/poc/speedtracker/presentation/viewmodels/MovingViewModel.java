package com.poc.speedtracker.presentation.viewmodels;

import android.app.Application;
import android.os.Handler;

import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.poc.speedtracker.data.model.LocationModel;
import com.poc.speedtracker.domain.services.LocationService;

import javax.inject.Inject;

public class MovingViewModel extends AndroidViewModel {
    private final LiveData<LocationModel> currentSpeedObservable;
    private final MutableLiveData<Boolean> userStoppedObservable;

    private Float averageSpeed = 0f;
    // Number of values used to compute the current average speed
    private int ticks = 0;

    private Handler stopHandler = new Handler();
    private Runnable stopActionRunnable = new Runnable() {
        @Override
        public void run() {
            userStoppedObservable.setValue(true);
        }
    };
    private Boolean userTemporaryStopped = false;

    private final LocationService locationService;

    @Inject
    public MovingViewModel(LocationService locationService, Application application) {
        super(application);

        this.locationService = locationService;
        currentSpeedObservable = locationService.getLocationData();
        userStoppedObservable = new MutableLiveData<>(false);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        locationService.stopLocation();
    }

    public LiveData<Boolean> userStopped() {
        return userStoppedObservable;
    }

    public LiveData<Boolean> userStartMoving() {
        return Transformations.map(getCurrentSpeed(), new Function<Float, Boolean>() {
            @Override
            public Boolean apply(Float input) {
                boolean isUserMovingAgain = input > 0;
                if (isUserMovingAgain) {
                    reset();
                }
                return isUserMovingAgain;
            }
        });
    }

    public LiveData<Float> getCurrentSpeed() {
        return Transformations.map(currentSpeedObservable, new Function<LocationModel, Float>() {
            @Override
            public Float apply(LocationModel input) {
                float currentSpeed = round(input.speed, 1);
                if (currentSpeed == 0) {
                    // Detects if the user is already stopped
                    if (!userTemporaryStopped) {
                        userTemporaryStopped = true;
                        // We will trigger the user stopped event only if he's stopped for 2 seconds
                        stopHandler.postDelayed(stopActionRunnable, 2000);
                    }
                } else {
                    // User is moving, cancel stopped event if planned
                    userTemporaryStopped = false;
                    stopHandler.removeCallbacks(stopActionRunnable);

                    // Compute new average speed value
                    ticks++;
                    averageSpeed = (averageSpeed * (ticks - 1) + currentSpeed) / ticks;
                }
                return currentSpeed;
            }
        });
    }

    public float getAverageSpeedOnLastSegment() {
        return averageSpeed;
    }

    private void reset() {
        userStoppedObservable.setValue(false);
        averageSpeed = 0f;
        ticks = 0;
    }

    private float round(float value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (float) Math.round(value * scale) / scale;
    }
}