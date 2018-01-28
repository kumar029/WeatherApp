package com.knr.myweatherapp.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.knr.myweatherapp.ui.fragments.ForecastFragment;

/**
 * Created by Kumar on 26-01-2018.
 */

public class ForecastAdapter extends FragmentStatePagerAdapter {
    public ForecastAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ForecastFragment.newInstance();
    }

    @Override
    public int getCount() {
        return 1;
    }
}
