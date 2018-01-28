package com.knr.myweatherapp.communication;


import com.knr.myweatherapp.presenter.model.WeatherResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Kumar on 26-01-2018.
 */

public interface CommunicationService {
    @GET("forecast")
    Call<WeatherResponseModel> getWeatherData(@Query("lat") double lat,@Query("lon") double log, @Query("APPID") String apiKey);

    @GET("forecast")
    Call<WeatherResponseModel> getWeatherForecast(@Query("q") String city, @Query("APPID") String apiKey);
}
