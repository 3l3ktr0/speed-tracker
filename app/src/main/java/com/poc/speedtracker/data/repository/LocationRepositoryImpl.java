package com.poc.speedtracker.data.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.poc.speedtracker.data.model.LocationLiveData;
import com.poc.speedtracker.data.model.LocationModel;
import com.poc.speedtracker.domain.repository.LocationRepository;

import javax.inject.Inject;

public class LocationRepositoryImpl implements LocationRepository {
    private final Context context;

    @Inject
    public LocationRepositoryImpl(@NonNull Context context) {
        this.context = context;
    }

    public LiveData<LocationModel> getLocationData() {
        return new LocationLiveData(context);
    }
}
