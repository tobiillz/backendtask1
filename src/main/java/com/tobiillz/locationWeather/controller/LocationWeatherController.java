package com.tobiillz.locationWeather.controller;

import com.tobiillz.locationWeather.dtos.WelcomeResponse;
import com.tobiillz.locationWeather.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hello")
public class LocationWeatherController {
    private final WelcomeService welcomeService;

    @Autowired
    public LocationWeatherController(WelcomeService welcomeService) {
        this.welcomeService = welcomeService;
    }

    @GetMapping
    public ResponseEntity<WelcomeResponse> welcome(@RequestParam(name = "visitor_name", required = false) String visitor, @RequestHeader(value = "X-Forwarded-For", required = false) String forwardedFor
    ) {
        return ResponseEntity.ok(welcomeService.welcome(visitor, forwardedFor));
    }
}
