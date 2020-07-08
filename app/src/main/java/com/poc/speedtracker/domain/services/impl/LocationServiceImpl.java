package com.poc.speedtracker.domain.services.impl;

import androidx.lifecycle.LiveData;

import com.poc.speedtracker.data.model.LocationModel;
import com.poc.speedtracker.data.repository.LocationRepository;
import com.poc.speedtracker.domain.services.LocationService;

public class LocationServiceImpl implements LocationService {
    private LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public LiveData<LocationModel> getLocationData() {
        return locationRepository.getLocationData();
    }

    @Override
    public void stopLocation() {
        locationRepository.stopLocation();
    }
}
