package com.grishberg.yandexweatherwear;

import android.widget.Toast;

import com.grishberg.androidweatherprovider.WeatherResponse;
import com.grishberg.wearcommunication.BaseListenerService;
import com.grishberg.weatherresponsewrapper.WeatherResponseWrapper;

import java.util.Locale;

/**
 * Created by bourdi_bay on 31/01/2015.
 */
public class WearWeatherListenerService extends BaseListenerService<WeatherResponseWrapper> {

    @Override
    protected WeatherResponseWrapper createPayloadFromBytes(byte[] bytes) {
        return WeatherResponseWrapper.weatherResponseFromBytes(bytes);
    }

    @Override
    protected void onMessageReceivedWithData(WeatherResponseWrapper data) {
        WeatherResponse weatherResponse = data.getWeatherResponse();
        Toast.makeText(getApplicationContext(),
                String.format(Locale.US, "%f C", weatherResponse.getCurrentTemperature()),
                Toast.LENGTH_SHORT).show();
    }
}
