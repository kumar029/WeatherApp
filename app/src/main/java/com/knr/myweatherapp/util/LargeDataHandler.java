package com.knr.myweatherapp.util;

import com.knr.myweatherapp.presenter.model.WeatherResponseModel;



/**
 * Created by Kumar on 26/01/18.
 *
 * @auther Kumar
 */

public class LargeDataHandler {
    public static LargeDataHandler largeDataHandler;
    WeatherResponseModel forecastList;
    public static synchronized LargeDataHandler getInstance() {
        if (largeDataHandler == null) {
            largeDataHandler = new LargeDataHandler();
        }
        return largeDataHandler;
    }

    public WeatherResponseModel getForecastList() {
        return forecastList;
    }

    public void setForecastList(WeatherResponseModel forecastList) {
        this.forecastList = forecastList;
    }
    public void clearWeatherData()
    {
        if(forecastList!=null)
        {
            forecastList=null;
        }
    }
}
