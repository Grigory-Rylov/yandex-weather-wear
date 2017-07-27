package com.grishberg.accuweatherlibrary;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.github.grigoryrylov.reststacklib.RetrofitBuilder;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.grishberg.accuweatherlibrary.models.CurrentConditionContainer;
import com.grishberg.androidweatherprovider.WeatherProvider;
import com.grishberg.androidweatherprovider.WeatherRequestParams;
import com.grishberg.androidweatherprovider.WeatherResponse;
import com.grishberg.androidweatherprovider.WeatherResponseListener;

import rx.schedulers.Schedulers;
import rx.android.schedulers.AndroidSchedulers;

import static com.grishberg.androidweatherprovider.WeatherRequestParams.RequestType.CURRENT_WEATHER;
import static com.grishberg.androidweatherprovider.WeatherRequestParams.RequestType.FORECAST;

/**
 * Created by grishberg on 23.07.17.
 */

public class AccuWeatherService implements WeatherProvider {
    private static final String TAG = AccuWeatherService.class.getSimpleName();
    private final Api api;
    private final String apiKey;
    private int locationId;
    @Nullable
    private WeatherResponseListener listener;

    public static AccuWeatherService createForLocationId(@NonNull Context context,
                                                         int locationId) {
        AccuWeatherService service = new AccuWeatherService(context);
        service.locationId = locationId;
        return service;
    }

    @SuppressWarnings("Unchecked")
    private AccuWeatherService(@NonNull Context context) {
        apiKey = context.getString(R.string.api_key);
        RetrofitBuilder<Api> builder = new RetrofitBuilder<>(context.getString(R.string.end_point),
                Api.class, new SoftErrorDelegateImpl());
        builder.setGson(new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .create());
        api = builder.provideService();
    }

    public void getCurrentWeather() {
        api.getCurrentCondition(locationId, apiKey)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        data -> {
                            //TODO: make response
                            listener.onWeatherResponse(getWeatherResponseFromCurrent(data[0]));
                        },
                        error -> {
                            Log.e(TAG, "getCurrentWeather: ", error);
                        }
                );
    }

    @NonNull
    private static WeatherResponse getWeatherResponseFromCurrent(CurrentConditionContainer currentConditionContainer) {
        float temperature = currentConditionContainer.getTemperature().getMetric().getValue();
        return new WeatherResponse(temperature,
                currentConditionContainer.getWeatherIcon(),
                CURRENT_WEATHER);
    }

    public void getForecast() {
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

    @Override
    public void setWeatherListener(WeatherResponseListener listener) {
        this.listener = listener;
    }

    @Override
    public void getWeather(@NonNull WeatherRequestParams params) {
        if (params.getRequestType() == CURRENT_WEATHER) {
            getCurrentWeather();
        } else if (params.getRequestType() == FORECAST) {
            getForecast();
        }
    }
}
