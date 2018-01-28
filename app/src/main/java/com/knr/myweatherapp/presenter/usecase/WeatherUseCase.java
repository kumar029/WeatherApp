package com.knr.myweatherapp.presenter.usecase;

import com.knr.myweatherapp.presenter.base.BaseCallback;
import com.knr.myweatherapp.presenter.base.Usecase;
import com.knr.myweatherapp.presenter.data.DataRepository;
import com.knr.myweatherapp.presenter.model.WeatherResponseModel;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Kumar on 26-01-2018.
 */

public class WeatherUseCase implements Usecase {
    private DataRepository dataRepository;
    Single<WeatherResponseModel> weatherModelSingle;
    private CompositeDisposable compositeDisposable;
    private Disposable baseDisposable;
    private DisposableSingleObserver<WeatherResponseModel> weatherModelDisposableSingleObserver;

    @Inject
    public WeatherUseCase(DataRepository dataRepository, CompositeDisposable compositeDisposable){
        this.dataRepository=dataRepository;
        this.compositeDisposable=compositeDisposable;
    }


    @Override
    public void getWeatherData(BaseCallback baseCallback, String type) {
        weatherModelDisposableSingleObserver=new DisposableSingleObserver<WeatherResponseModel>() {
            @Override
            public void onSuccess(WeatherResponseModel weatherResponseModel) {
                baseCallback.onWeatherSuccess(weatherResponseModel);
            }

            @Override
            public void onError(Throwable e) {
                baseCallback.onFail();
                e.printStackTrace();
            }
        };
        if (!compositeDisposable.isDisposed()) {
            weatherModelSingle = dataRepository.onWeatherApiCall(type);
            baseDisposable = weatherModelSingle.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(weatherModelDisposableSingleObserver);
            compositeDisposable.add(baseDisposable);
        }
    }

    @Override
    public void getGpsWeatherData(BaseCallback baseCallback, double lat, double log) {
        weatherModelDisposableSingleObserver=new DisposableSingleObserver<WeatherResponseModel>() {
            @Override
            public void onSuccess(WeatherResponseModel weatherResponseModel) {
                baseCallback.onWeatherSuccess(weatherResponseModel);
            }

            @Override
            public void onError(Throwable e) {
                baseCallback.onFail();
                e.printStackTrace();
            }
        };
        if (!compositeDisposable.isDisposed()) {
            weatherModelSingle = dataRepository.onGpsWeatherApiCall(lat,log);
            baseDisposable = weatherModelSingle.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(weatherModelDisposableSingleObserver);
            compositeDisposable.add(baseDisposable);
        }
    }

    public void unSubscribe(){
        if (!compositeDisposable.isDisposed()) {
            if(baseDisposable!=null) {
                compositeDisposable.remove(baseDisposable);
            }
        }
    }
}
