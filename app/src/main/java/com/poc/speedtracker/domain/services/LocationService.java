package com.poc.speedtracker.domain.services;

import androidx.lifecycle.LiveData;

public interface LocationService {
    public LiveData<Float> getCurrentSpeed();
}
