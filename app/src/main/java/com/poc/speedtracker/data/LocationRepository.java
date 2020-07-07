package com.poc.speedtracker.data;

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

public class LocationRepository {

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

    public LocationRepository(@NonNull Context context) {
        fusedLocationProviderClient = new FusedLocationProviderClient(context);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @SuppressLint("MissingPermission")
    public LiveData<LocationModel> getLocationData() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                setLocationData(location);
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

    private void setLocationData(Location location) {
        data.setValue(new LocationModel(location.getLongitude(),
                location.getLatitude(),
                location.getSpeed()));
    }
}

