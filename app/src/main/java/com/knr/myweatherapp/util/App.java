package com.knr.myweatherapp.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by Kumar on 26-01-2018.
 */

public class App extends Application {
    private MainComponent mainComponent;
    //    private PreferencesComponent sessionComponent;
    private static Context context;
    private MainComponent sessionPrefrenceComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mainComponent = DaggerMainComponent.builder().mainModule(new MainModule()).build();
        context = getApplicationContext();
    }
    public MainComponent getMainComponent() {
        return mainComponent;
    }
    public static Context getContext() {
        return context;
    }
    public MainComponent getPermissionComponentPreference()
    {
        if(sessionPrefrenceComponent==null) {
            sessionPrefrenceComponent = DaggerMainComponent.builder().mainModule(new MainModule(getApplicationContext(), Constants.PERMISSION_STATUS)).build();
        }
        return sessionPrefrenceComponent;
    }
}
