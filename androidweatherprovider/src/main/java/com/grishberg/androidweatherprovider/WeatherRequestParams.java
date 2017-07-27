package com.grishberg.androidweatherprovider;

/**
 * Created by grishberg on 21.07.17.
 */
public class WeatherRequestParams {
    public enum RequestType {
        CURRENT_WEATHER,
        FORECAST,
        BOTH
    }

    private final RequestType requestType;

    public WeatherRequestParams(RequestType requestType) {
        this.requestType = requestType;
    }

    public static WeatherRequestParams createCurrentWeatherRequest() {
        return new WeatherRequestParams(RequestType.CURRENT_WEATHER);
    }

    public RequestType getRequestType() {
        return requestType;
    }
}
