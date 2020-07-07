package com.poc.speedtracker.presentation.moving;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.location.LocationServices;
import com.poc.speedtracker.data.LocationModel;
import com.poc.speedtracker.data.LocationRepository;
import com.poc.speedtracker.domain.services.LocationService;
import com.poc.speedtracker.domain.services.impl.LocationServiceImpl;

public class MovingViewModel extends AndroidViewModel {
    private final LiveData<LocationModel> currentSpeedObservable;
    private final MutableLiveData<Boolean> userStoppedObservable;

    private Float averageSpeed = 0f;
    private int ticks = 0;

    private Handler stopHandler = new Handler();
    private Runnable stopActionRunnable = new Runnable() {
        @Override
        public void run() {
            userStoppedObservable.setValue(true);
        }
    };
    private Boolean userTemporaryStopped = false;

    private LocationService locationService;

    public MovingViewModel(Application application) {
        super(application);
        // If any transformation is needed, this can be simply done by Transformations class ...
        locationService = new LocationServiceImpl(new LocationRepository(application));
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
                    if (!userTemporaryStopped) {
                        userTemporaryStopped = true;
                        stopHandler.postDelayed(stopActionRunnable, 2000);
                    }
                } else {
                    userTemporaryStopped = false;
                    stopHandler.removeCallbacks(stopActionRunnable);

                    // Compute new average speed value
                    Log.d("Location", input.toString());


                    ticks++;
                    averageSpeed = (averageSpeed * (ticks - 1) + currentSpeed) / ticks;

                    Log.d("Average speed", averageSpeed + "");
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