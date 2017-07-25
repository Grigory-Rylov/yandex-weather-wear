package com.grishberg.accuweatherlibrary;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.grishberg.weather.WeatherResponse;
import com.grishberg.weather.WeatherResponseListener;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Created by grishberg on 24.07.17.
 */
@RunWith(AndroidJUnit4.class)
public class WeatherServiceTest {
    private WeatherService weatherService;
    private Context appContext;

    @Before
    public void setUp() throws Exception {
        appContext = InstrumentationRegistry.getTargetContext();
        weatherService = WeatherService.createForLocationId(appContext, 295212);
    }

    @Test
    public void getCurrentWeather() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        WeatherResponseListener listener = new WeatherResponseListener() {
            @Override
            public void onWeatherResponse(WeatherResponse response) {
                countDownLatch.countDown();
            }
        };
        weatherService.getCurrentWeather(listener);
        assertTrue(countDownLatch.await(5, TimeUnit.SECONDS));
    }

    @Test
    public void getForecastWeather() throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        WeatherResponseListener listener = new WeatherResponseListener() {
            @Override
            public void onWeatherResponse(WeatherResponse response) {
                countDownLatch.countDown();
            }
        };
        weatherService.getForecast(listener);
        assertTrue(countDownLatch.await(5, TimeUnit.SECONDS));
    }
}