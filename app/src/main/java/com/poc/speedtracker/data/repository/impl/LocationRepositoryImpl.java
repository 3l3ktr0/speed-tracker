package com.poc.speedtracker.data.repository.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnSuccessListener;
import com.poc.speedtracker.data.model.LocationModel;
import com.poc.speedtracker.data.repository.LocationRepository;

import javax.inject.Inject;

public class LocationRepositoryImpl implements LocationRepository {

    private static final int LOCATION_INTERVAL = 1000;
    private static final int LOCATION_FASTEST_INTERVAL = 500;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private final LocationRequest locationRequest = new LocationRequest();
    private final MutableLiveData<LocationModel> data = new MutableLiveData<>();

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            for (Location location : locationResult.getLocations()) {
                setLocationData(location);
            }
        }
    };

    @Inject
    public LocationRepositoryImpl(@NonNull Context context) {
        fusedLocationProviderClient = new FusedLocationProviderClient(context);
        locationRequest.setInterval(LOCATION_INTERVAL);
        locationRequest.setFastestInterval(LOCATION_FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @SuppressLint("MissingPermission")
    public LiveData<LocationModel> getLocationData() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    setLocationData(location);
                }
                startLocationUpdates();
            }
        });
        return data;
    }

    public void stopLocation() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
        );
    }

    private void setLocationData(@NonNull Location location) {
        data.setValue(new LocationModel(location.getLongitude(),
                location.getLatitude(),
                location.getSpeed() * 3.6f));
    }
}

