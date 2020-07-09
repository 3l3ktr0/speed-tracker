package com.poc.speedtracker.data.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationLiveData extends LiveData<LocationModel> {
    private static final int LOCATION_INTERVAL = 1000;
    private static final int LOCATION_FASTEST_INTERVAL = 500;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private final LocationRequest locationRequest = new LocationRequest();

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            for (Location location : locationResult.getLocations()) {
                setLocationData(location);
            }
        }
    };

    public LocationLiveData(Context context) {
        fusedLocationProviderClient = new FusedLocationProviderClient(context);
        locationRequest.setInterval(LOCATION_INTERVAL);
        locationRequest.setFastestInterval(LOCATION_FASTEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onActive() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    setLocationData(location);
                }
                startLocationUpdates();
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
        );
    }

    @Override
    protected void onInactive() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void setLocationData(@NonNull Location location) {
        setValue(new LocationModel(location.getLongitude(),
                location.getLatitude(),
                location.getSpeed() * 3.6f));
    }
}