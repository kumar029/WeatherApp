package com.knr.myweatherapp.presenter;

import com.knr.myweatherapp.presenter.base.BaseCallback;
import com.knr.myweatherapp.presenter.base.Presenter;
import com.knr.myweatherapp.presenter.interfaces.WeatherCallback;
import com.knr.myweatherapp.presenter.interfaces.WeatherContract;
import com.knr.myweatherapp.presenter.usecase.WeatherUseCase;

import javax.inject.Inject;

/**
 * Created by Kumar on 26-01-2018.
 */

public class WeatherPresenter extends Presenter<WeatherContract.View> implements WeatherCallback {
    private final WeatherUseCase weatherUsecase;
    @Inject
    public WeatherPresenter(WeatherUseCase weatherUsecase){
        this.weatherUsecase=weatherUsecase;
    }
    @Override
    public void onWeatherData(String city) {
        weatherUsecase.getWeatherData(callback,city);
    }

    @Override
    public void onGpsWeatherData(double lat, double log) {
        weatherUsecase.getGpsWeatherData(callback,lat,log);
    }

    @Override
    public void unSubscribe() {
        weatherUsecase.unSubscribe();
    }
    private final BaseCallback callback=new BaseCallback() {



        @Override
        public void onWeatherSuccess(Object model) {
            getView().onWeatherSuccess(model);
        }

        @Override
        public void onFail() {
            getView().onFail();
        }
    };
}
