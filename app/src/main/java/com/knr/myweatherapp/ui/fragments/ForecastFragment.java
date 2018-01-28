package com.knr.myweatherapp.ui.fragments;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.knr.myweatherapp.R;
import com.knr.myweatherapp.ui.adapters.DaysForecastAdapter;
import com.knr.myweatherapp.ui.base.BaseFragment;
import com.knr.myweatherapp.util.App;

import butterknife.BindView;

/**
 * Created by Kumar on 26-01-2018.
 */

public class ForecastFragment extends BaseFragment {
    @BindView(R.id.recycler_view_fragment)
    RecyclerView recyclerView;
    @Override
    protected void initializeDagger() {
        App app = (App) getActivity().getApplicationContext();
        app.getMainComponent().inject(ForecastFragment.this);
    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    protected void initializeActivity() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new DaysForecastAdapter(getActivity()));
    }

    @Override
    public int getLayoutId() {
        return R.layout.forecast_recyclerview;
    }
    public static ForecastFragment newInstance(){
        ForecastFragment forecastFragment=new ForecastFragment();
        return forecastFragment;
    }
}
