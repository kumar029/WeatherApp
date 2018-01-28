package com.knr.myweatherapp.communication;

import android.accounts.NetworkErrorException;
import android.os.Build;
import android.util.Log;

import com.knr.myweatherapp.presenter.model.WeatherResponseModel;
import com.knr.myweatherapp.util.App;
import com.knr.myweatherapp.util.Constants;

import java.io.IOException;

import javax.inject.Inject;


import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Call;
import retrofit2.Response;

import static com.knr.myweatherapp.communication.ServiceError.NETWORK_ERROR;
import static com.knr.myweatherapp.communication.ServiceError.SUCCESS_CODE;
import static com.knr.myweatherapp.util.Constants.ERROR_UNDEFINED;
import static com.knr.myweatherapp.util.NetworkUtils.isConnected;

import static java.util.Objects.isNull;

/**
 * Created by Kumar on 26-01-2018.
 */

public class RemoteRepository implements RemoteSource {
    private ServiceGenerator serviceGenerator;
    private final String UNDELIVERABLE_EXCEPTION_TAG="Unknown error occur";

    @Inject
    public RemoteRepository(ServiceGenerator serviceGenerator){
        this.serviceGenerator=serviceGenerator;
    }
    @Override
    public Single getWeatherData(String city) {

        RxJavaPlugins.setErrorHandler(throwable -> {
            Log.i(UNDELIVERABLE_EXCEPTION_TAG, throwable.getMessage());
            return;
        });
        Single<WeatherResponseModel> homeModelSingle = Single.create(singleOnSubscribe -> {
                    if (!isConnected(App.getContext())) {
                        Exception exception = new NetworkErrorException();
                        singleOnSubscribe.onError(exception);
                    } else {
                        try {
                            CommunicationService newsService = serviceGenerator.createService(CommunicationService.class, Constants.BASE_URL);

                            ServiceResponse serviceResponse = processCall(newsService.getWeatherForecast(city, Constants.API_KEY),false);

                            if (serviceResponse.getCode() == SUCCESS_CODE) {
                                WeatherResponseModel loginResponseModel = (WeatherResponseModel) serviceResponse.getData();
                                singleOnSubscribe.onSuccess(loginResponseModel);
                            } else {
                                Throwable throwable = new NetworkErrorException();
                                singleOnSubscribe.onError(throwable);
                                throwable.printStackTrace();
                            }
                        } catch (Exception e) {
                            singleOnSubscribe.onError(e);
                            e.printStackTrace();
                        }
                    }
                }
        );
        return homeModelSingle;
    }

    @Override
    public Single getGpsWeatherData(double lat, double log) {
        RxJavaPlugins.setErrorHandler(throwable -> {
            Log.i(UNDELIVERABLE_EXCEPTION_TAG, throwable.getMessage());
            return;
        });
        Single<WeatherResponseModel> homeModelSingle = Single.create(singleOnSubscribe -> {
                    if (!isConnected(App.getContext())) {
                        Exception exception = new NetworkErrorException();
                        singleOnSubscribe.onError(exception);
                    } else {
                        try {
                            CommunicationService newsService = serviceGenerator.createService(CommunicationService.class, Constants.BASE_URL);

                            ServiceResponse serviceResponse = processCall(newsService.getWeatherData(lat,log, Constants.API_KEY),false);

                            if (serviceResponse.getCode() == SUCCESS_CODE) {
                                WeatherResponseModel loginResponseModel = (WeatherResponseModel) serviceResponse.getData();
                                singleOnSubscribe.onSuccess(loginResponseModel);
                            } else {
                                Throwable throwable = new NetworkErrorException();
                                singleOnSubscribe.onError(throwable);
                                throwable.printStackTrace();
                            }
                        } catch (Exception e) {
                            singleOnSubscribe.onError(e);
                            e.printStackTrace();
                        }
                    }
                }
        );
        return homeModelSingle;
    }


    @NonNull
    private ServiceResponse processCall(Call call, boolean isVoid) {

        if (!isConnected(App.getContext())) {

            return new ServiceResponse(new ServiceError());
        }
        try {
            Response response = call.execute();
//            Gson gson = new Gson();
//            L.json(NewsModel.class.getName(), gson.toJson(response.body()));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (isNull(response)) {

                    return new ServiceResponse(new ServiceError(NETWORK_ERROR, ERROR_UNDEFINED));
                }
            }
            int responseCode = response.code();
            if (response.isSuccessful()) {

                return new ServiceResponse(responseCode, isVoid ? null : response.body());
            } else {

                ServiceError ServiceError;
                ServiceError = new ServiceError(response.message(), responseCode);
                return new ServiceResponse(ServiceError);
            }
        } catch (IOException e) {

            e.printStackTrace();
            return new ServiceResponse(new ServiceError(NETWORK_ERROR, ERROR_UNDEFINED));
        }
    }

}
