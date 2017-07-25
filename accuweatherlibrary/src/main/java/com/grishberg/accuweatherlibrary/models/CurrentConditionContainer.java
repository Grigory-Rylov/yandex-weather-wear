package com.grishberg.accuweatherlibrary.models;

import lombok.Data;

/**
 * Created by grishberg on 23.07.17.
 */
@Data
public class CurrentConditionContainer {
    String localObservationDateTime;
    long epochTime;
    String weatherText;
    int weatherIcon;
    boolean isDayTime;
    Temperature temperature;
}
