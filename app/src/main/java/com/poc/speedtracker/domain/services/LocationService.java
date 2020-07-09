package com.poc.speedtracker.domain.services;

import androidx.lifecycle.LiveData;

import com.poc.speedtracker.data.model.LocationModel;

public interface LocationService {
    LiveData<LocationModel> getLocationData();
}
