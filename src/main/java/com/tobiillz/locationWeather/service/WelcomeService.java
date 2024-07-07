package com.tobiillz.locationWeather.service;

import com.tobiillz.locationWeather.dtos.WelcomeResponse;
import com.tobiillz.locationWeather.handler.NameNotFoundException;
import com.tobiillz.locationWeather.model.GeoLocation;
import com.tobiillz.locationWeather.model.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Service
@Slf4j
public class WelcomeService {


    @Value("${ip.geolocation.key}")
    private String ipGeolocationApiKey;

    @Value("${weather.api.key}")
    private String WeatherMapApiKey;

    private final RestTemplate restTemplate;
    private final HttpServletRequest request;

    @Autowired
    public WelcomeService(RestTemplate restTemplate, HttpServletRequest request) {
        this.restTemplate = restTemplate;
        this.request = request;
    }


    public WelcomeResponse welcome(String visitor, String ipAddress) {

        if (visitor == null) {
            throw new NameNotFoundException("Oops! Seems you forgot to add your name using " +
                    "the 'visitor_name' query parameter");
        }

        if (visitor.isEmpty()) {
            throw new NameNotFoundException("Sorry, 'visitor_name' query parameter cannot be empty");
        }

        String realIpAddress = ipAddress != null ? ipAddress : getClientIpAddress(request);

        GeoLocation geoLocation = getGeo(realIpAddress);

        double lat = geoLocation.getLatitude();
        double lon = geoLocation.getLongitude();
        String ip_address = geoLocation.getIp();

        log.info("latitude " + lat);
        log.info("longitude " + lon);
        log.info("IPADDRESS " + ip_address);

        Weather weather = getWeather(lat, lon, WeatherMapApiKey);
        double temp = (double) weather.getMain().get("temp");
        String city = geoLocation.getCity();
        String state = geoLocation.getState_prov();
        String country = geoLocation.getCountry_name();
        String sanitizedName = visitor.replaceAll("\"", "").trim();

        return new WelcomeResponse(
                ip_address,
                city + "," + state,
                "Hello, " + sanitizedName + "!, the temperature is " + temp + " degrees Celsius in " + city + "," + state + " " + country
        );
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String header = request.getHeader("X-Forwarded-For");
        if (header == null || header.isEmpty()) {
            return request.getRemoteAddr();
        }
        return header.split(",")[0];
    }

    public GeoLocation getGeo(String ip) {
        String locationUrl = String.format("https://api.ipgeolocation.io/ipgeo?apiKey=%s", ipGeolocationApiKey, ip);

        log.info("log>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + locationUrl);
        return restTemplate.getForObject(locationUrl, GeoLocation.class);
    }

    public Weather getWeather(double lat, double lon, String apiKey) {
        String weatherUrl = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s&units=metric", lat, lon, apiKey);

        return restTemplate.getForObject(weatherUrl, Weather.class);
    }


}
