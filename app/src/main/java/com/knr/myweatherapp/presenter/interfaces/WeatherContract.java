package com.knr.myweatherapp.presenter.interfaces;


import com.knr.myweatherapp.presenter.base.BaseView;

/**
 * Created by Kumar on 21-11-2017.
 */

public interface WeatherContract {

    interface View<M> extends BaseView {
        void onWeatherSuccess(M model);
        void onFail();
    }
}
