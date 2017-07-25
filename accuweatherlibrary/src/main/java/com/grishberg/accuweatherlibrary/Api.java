package com.grishberg.accuweatherlibrary;

import com.grishberg.accuweatherlibrary.models.CurrentConditionContainer;
import com.grishberg.accuweatherlibrary.models.ForecastContainer;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by grishberg on 23.07.17.
 */

public interface Api {
    @GET("currentconditions/v1/{locationKey}")
    Observable<CurrentConditionContainer[]> getCurrentCondition(
            @Path("locationKey") long customerId,
            @Query("apikey") String sprintToken
    );

    @GET("forecasts/v1/daily/1day/{locationKey}")
    Observable<ForecastContainer> getForecast(
            @Path("locationKey") long customerId,
            @Query("apikey") String sprintToken,
            @Query("metric") boolean metric
    );
}
