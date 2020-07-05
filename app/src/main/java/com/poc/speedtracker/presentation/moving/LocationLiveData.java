package com.poc.speedtracker.presentation.moving;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.tasks.OnSuccessListener;
import com.poc.speedtracker.data.LocationModel;

public class LocationLiveData extends LiveData<LocationModel> {
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
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onActive() {
        super.onActive();
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                setLocationData(location);
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
    private void setLocationData(Location location) {

        Log.d("Speed", location.getSpeed()+"");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("Speed", location.getSpeedAccuracyMetersPerSecond()+"");
        }
        Log.d("Speed", location.hasSpeed()+"");

        setValue(new LocationModel(location.getLongitude(), location.getLatitude()));
    }
}