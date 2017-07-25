package com.grishberg.accuweatherlibrary.models;

import lombok.Data;

/**
 * Created by grishberg on 24.07.17.
 */
@Data
public class DailyForecast {
    long epochDate;
    ForecastTemperature temperature;
    TimesOfDay day;
    TimesOfDay night;
}
