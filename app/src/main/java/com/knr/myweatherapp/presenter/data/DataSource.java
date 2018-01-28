package com.knr.myweatherapp.presenter.data;

import java.util.Map;

import io.reactivex.Single;

/**
 * Created by Kumar on 27-01-2018.
 */

public interface DataSource {

Single onWeatherApiCall(String city);
Single onGpsWeatherApiCall(double lat, double log);

}
