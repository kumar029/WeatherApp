package com.knr.myweatherapp.presenter.data;

import android.util.Log;

import com.knr.myweatherapp.communication.RemoteRepository;

import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by Kumar on 27-01-2018.
 */

public class DataRepository implements DataSource {

    private RemoteRepository remoteRepository;
    @Inject
    public DataRepository(RemoteRepository remoteRepository){
        this.remoteRepository=remoteRepository;
    }
    @Override
    public Single onWeatherApiCall(String city) {
        return remoteRepository.getWeatherData(city);
    }

    @Override
    public Single onGpsWeatherApiCall(double lat, double log) {
        return remoteRepository.getGpsWeatherData(lat, log);
    }


}
