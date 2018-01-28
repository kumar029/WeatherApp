package com.knr.myweatherapp.ui.adapters.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.knr.myweatherapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Kumar on 26-01-2018.
 */

public class DaysForecastViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.place)
    public TextView place;

    @BindView(R.id.temperature)
    public TextView temperature;
    @BindView(R.id.description)
    public TextView description;
    @BindView(R.id.date)
    public TextView date;
    @BindView(R.id.wind)
    public TextView wind;
    @BindView(R.id.pressure)
    public TextView pressure;
    @BindView(R.id.humidity)
    public TextView humidity;
    @BindView(R.id.coordinates)
    public TextView coordinates;
    public DaysForecastViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
}
