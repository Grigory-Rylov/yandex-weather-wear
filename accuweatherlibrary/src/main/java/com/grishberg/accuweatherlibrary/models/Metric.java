package com.grishberg.accuweatherlibrary.models;

import lombok.Data;

/**
 * Created by grishberg on 23.07.17.
 */
@Data
public class Metric {
    float value;
    String unit;
    int unitType;
}
