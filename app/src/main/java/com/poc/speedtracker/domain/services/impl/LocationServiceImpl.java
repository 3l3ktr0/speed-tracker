package com.poc.speedtracker.domain.services.impl;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.poc.speedtracker.data.LocationModel;
import com.poc.speedtracker.data.LocationRepository;
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
