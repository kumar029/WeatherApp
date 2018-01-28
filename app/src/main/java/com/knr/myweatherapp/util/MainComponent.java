package com.knr.myweatherapp.util;

import com.knr.myweatherapp.ui.GPSLocationActivity;
import com.knr.myweatherapp.ui.MainActivity;
import com.knr.myweatherapp.ui.fragments.ForecastFragment;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Created by Kumar on 21-11-2017.
 */
@Singleton
@Component (modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity mainActivity);
    void inject(ForecastFragment forecastFragment);
    void inject(GPSLocationActivity gpsLocationActivity);

}
