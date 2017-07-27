package com.grishberg.androidweatherprovider;

public interface WeatherProvider {
    void setWeatherListener(WeatherResponseListener listener);

    void getWeather(WeatherRequestParams params);
}
