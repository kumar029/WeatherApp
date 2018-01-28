package com.knr.myweatherapp.presenter.base;

import java.util.Map;

/**
 * Created by Kumar on 27-01-2018.
 */

public interface Usecase {

    void getWeatherData(BaseCallback baseCallback, String type);
    void getGpsWeatherData(BaseCallback baseCallback, double lat, double log);
}
