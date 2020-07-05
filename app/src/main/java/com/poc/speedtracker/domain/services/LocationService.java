package com.poc.speedtracker.domain.services;

import androidx.lifecycle.LiveData;

import com.poc.speedtracker.data.LocationModel;

public interface LocationService {
    LiveData<LocationModel> getLocationData();
    void stopLocation();
}
