package com.grishberg.yandexweatherwear;

import com.grishberg.accuweatherlibrary.AccuWeatherService;
import com.grishberg.androidweatherprovider.WeatherProvider;
import com.grishberg.androidweatherprovider.WeatherRequestParams;
import com.grishberg.androidweatherprovider.WeatherResponse;
import com.grishberg.androidweatherprovider.WeatherResponseListener;
import com.grishberg.wearcommunication.BaseListenerService;
import com.grishberg.weatherresponsewrapper.WeatherResponseWrapper;

public class PhoneWeatherListenerService extends BaseListenerService<WeatherResponseWrapper>
        implements WeatherResponseListener {

    public static final int LOCATION_ID = 295212;
    private WeatherProvider weatherProvider;

    @Override
    public void onCreate() {
        super.onCreate();
        this.weatherProvider = AccuWeatherService.createForLocationId(getApplicationContext(),
                LOCATION_ID);
        weatherProvider.setWeatherListener(this);
    }

    @Override
    protected WeatherResponseWrapper createPayloadFromBytes(byte[] bytes) {
        return WeatherResponseWrapper.weatherResponseFromBytes(bytes);
    }

    @Override
    protected void onMessageReceivedWithData(WeatherResponseWrapper data) {
        weatherProvider.getWeather(WeatherRequestParams.createCurrentWeatherRequest());
    }

    @Override
    public void onWeatherResponse(WeatherResponse response) {
        sendMessage(new WeatherResponseWrapper(response));
    }
}

