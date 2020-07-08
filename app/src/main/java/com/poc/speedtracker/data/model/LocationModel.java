package com.poc.speedtracker.data.model;

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

    @Override
    public String toString() {
        return "LocationModel{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", speed=" + speed +
                '}';
    }
}
