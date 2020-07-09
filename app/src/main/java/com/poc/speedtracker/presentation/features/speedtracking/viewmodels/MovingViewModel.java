package com.poc.speedtracker.presentation.features.speedtracking.viewmodels;

import android.os.Handler;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.poc.speedtracker.data.model.LocationModel;
import com.poc.speedtracker.domain.services.LocationService;

import javax.inject.Inject;

public class MovingViewModel extends ViewModel {
    /**
     * We will trigger the user stopped event only if he's stopped for 2 seconds
     */
    private static final int DELAY_STOP_EVENT = 2000;

    private final LiveData<LocationModel> currentSpeedObservable;
    private boolean userStopped = false;

    private Float averageSpeed = 0f;
    // Number of values used to compute the current average speed
    private int ticks = 0;

    private Handler stopHandler = new Handler();
    private Runnable stopActionRunnable = new Runnable() {
        @Override
        public void run() {
            userStopped = true;
        }
    };
    private Boolean userTemporaryStopped = false;

    @Inject
    public MovingViewModel(LocationService locationService) {
        currentSpeedObservable = locationService.getLocationData();
    }

    public LiveData<Boolean> userStartMoving() {
        return Transformations.map(getCurrentSpeed(), new Function<Float, Boolean>() {
            @Override
            public Boolean apply(Float input) {
                boolean isUserMovingAgain = input > 0 && userStopped;
                if (isUserMovingAgain) {
                    reset();
                }
                return input > 0 || !userStopped;
            }
        });
    }

    public LiveData<Float> getCurrentSpeed() {
        return Transformations.map(currentSpeedObservable, new Function<LocationModel, Float>() {
            @Override
            public Float apply(LocationModel input) {
                float currentSpeed = round(input.speed, 1);
                if (currentSpeed < 1) {
                    // Detects if the user is already stopped
                    if (!userTemporaryStopped) {
                        userTemporaryStopped = true;
                        // We will trigger the user stopped event only if he's stopped for 2 seconds
                        stopHandler.postDelayed(stopActionRunnable, DELAY_STOP_EVENT);
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
        userStopped = false;
        averageSpeed = 0f;
        ticks = 0;
    }

    private float round(float value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (float) Math.round(value * scale) / scale;
    }
}