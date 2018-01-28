package com.knr.myweatherapp.presenter.base;

/**
 * Created by Kumar on 27-01-2018.
 */

public interface BaseCallback <M>{
    void onWeatherSuccess(M model);

    void onFail();
}
