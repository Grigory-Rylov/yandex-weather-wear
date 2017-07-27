package com.grishberg.weatherresponsewrapper;

import com.grishberg.androidweatherprovider.WeatherResponse;
import com.grishberg.wearcommunication.WearMessage;

/**
 * Created by grishberg on 27.07.17.
 */

public class WeatherResponseWrapper implements WearMessage {
    private final WeatherResponse weatherResponse;

    public WeatherResponseWrapper(WeatherResponse weatherResponse) {
        this.weatherResponse = weatherResponse;
    }

    @Override
    public byte[] toBytes() {
        return weatherResponse.toBytes();
    }

    public static WeatherResponseWrapper weatherResponseFromBytes(byte[] bytes) {
        return new WeatherResponseWrapper(WeatherResponse.weatherResponseFromBytes(bytes));
    }

    public WeatherResponse getWeatherResponse() {
        return weatherResponse;
    }
}
