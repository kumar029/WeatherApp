package com.knr.myweatherapp.communication;

import java.util.Map;

import io.reactivex.Single;

/**
 * Created by Kumar on 26-01-2018.
 */

interface RemoteSource {
    Single getWeatherData(String city);
    Single getGpsWeatherData(double lat,double log);
}
