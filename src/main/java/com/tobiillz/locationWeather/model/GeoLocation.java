package com.tobiillz.locationWeather.model;

import lombok.Data;

@Data
public class GeoLocation {

    private String ip;
    private double latitude;
    private double longitude;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public GeoLocation(String ip, double latitude, double longitude) {
        this.ip = ip;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
