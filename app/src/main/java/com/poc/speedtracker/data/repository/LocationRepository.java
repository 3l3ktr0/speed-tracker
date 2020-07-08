package com.poc.speedtracker.data.repository;

import androidx.lifecycle.LiveData;

import com.poc.speedtracker.data.model.LocationModel;

public interface LocationRepository {
    LiveData<LocationModel> getLocationData();

    void stopLocation();
}

