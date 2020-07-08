package com.poc.speedtracker.data.model;

import androidx.annotation.NonNull;

public class LocationModel {
    public Double longitude;
    public Double latitude;
    public float speed;

    public LocationModel(Double longitude,
                         Double latitude,
                         float speed) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.speed = speed;
    }

    @NonNull
    @Override
    public String toString() {
        return "LocationModel{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", speed=" + speed +
                '}';
    }
}
