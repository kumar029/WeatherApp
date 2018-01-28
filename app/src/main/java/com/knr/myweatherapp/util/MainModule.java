package com.knr.myweatherapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Kumar on 21-11-2017.
 */
@Module
public class MainModule {
    public MainModule() {

    }

    @Provides
    @Singleton
    public Gson provideGson() {
        Gson gson = new GsonBuilder().create();
        return gson;
    }

    @Provides
    public CompositeDisposable provideCompositeSubscription() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        return compositeDisposable;
    }

    private Context context;
    private String prefName;

    public MainModule(Context context,String prefName) {
        this.context = context;
        this.prefName=prefName;
    }

    @Provides
    SharedPreferences provideSharedPreferences() {
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

}
