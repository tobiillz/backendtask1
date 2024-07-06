package com.tobiillz.locationWeather.model;

import lombok.Data;

@Data
public class GeoLocation {

    private String ip;
    private double latitude;
    private double longitude;
    private String city;
    private String state_prov;
    private String country_name;
}
