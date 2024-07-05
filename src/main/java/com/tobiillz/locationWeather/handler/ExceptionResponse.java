package com.tobiillz.locationWeather.handler;

import lombok.Data;

@Data
public class ExceptionResponse {
    String error;

    public ExceptionResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
