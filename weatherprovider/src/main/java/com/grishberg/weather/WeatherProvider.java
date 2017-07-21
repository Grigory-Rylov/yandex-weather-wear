package com.grishberg.weather;

public interface WeatherProvider {
    void getWeather(WeatherRequestParams params, WeatherResponseListener listener);
}
