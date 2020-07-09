package com.poc.speedtracker.domain.repository;

import androidx.lifecycle.LiveData;

import com.poc.speedtracker.data.model.LocationModel;

public interface LocationRepository {
    LiveData<LocationModel> getLocationData();
}

