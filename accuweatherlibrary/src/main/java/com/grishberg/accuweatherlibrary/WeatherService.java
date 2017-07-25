package com.grishberg.accuweatherlibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.github.grigoryrylov.reststacklib.RetrofitBuilder;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.grishberg.weather.WeatherResponseListener;

import rx.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by grishberg on 23.07.17.
 */

public class WeatherService {
    private static final String TAG = WeatherService.class.getSimpleName();
    private final Api api;
    private final String apiKey;
    private int locationId;

    public static WeatherService createForLocationId(@NonNull Context context,
                                                     int locationId) {
        WeatherService service = new WeatherService(context);
        service.locationId = locationId;
        return service;
    }

    @SuppressWarnings("Unchecked")
    private WeatherService(@NonNull Context context) {
        apiKey = context.getString(R.string.api_key);
        RetrofitBuilder<Api> builder = new RetrofitBuilder<>(context.getString(R.string.end_point),
                Api.class, new SoftErrorDelegateImpl());
        builder.setGson(new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create());
        api = builder.provideService();
    }

    public void getCurrentWeather(@NonNull final WeatherResponseListener listener) {
        api.getCurrentCondition(locationId, apiKey)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> {
                            //TODO: make response
                            listener.onWeatherResponse(null);
                        },
                        error -> {
                            Log.e(TAG, "getCurrentWeather: ", error);
                        }
                );
    }

    public void getForecast(@NonNull final WeatherResponseListener listener) {
        api.getForecast(locationId, apiKey, true)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> {
                            //TODO: make response
                            listener.onWeatherResponse(null);
                        },
                        error -> {
                            Log.e(TAG, "getCurrentWeather: ", error);
                        }
                );
    }
}
