package com.tobiillz.locationWeather.model;

import lombok.Data;

import java.util.Map;

@Data
public class Weather {
    String name;
    Map<String, Object> main;
}
