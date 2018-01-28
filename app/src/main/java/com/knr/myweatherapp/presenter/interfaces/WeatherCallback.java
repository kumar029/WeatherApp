package com.knr.myweatherapp.presenter.interfaces;

import java.util.Map;

/**
 * Created by Kumar on 21-11-2017.
 */

public interface WeatherCallback {
   void onWeatherData(String city);
   void onGpsWeatherData(double lat, double log);
   void unSubscribe();
}
