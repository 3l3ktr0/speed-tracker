package com.poc.speedtracker.data;

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
}
