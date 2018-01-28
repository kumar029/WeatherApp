package com.knr.myweatherapp.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knr.myweatherapp.R;
import com.knr.myweatherapp.presenter.model.Weather;
import com.knr.myweatherapp.presenter.model.WeatherResponseModel;
import com.knr.myweatherapp.ui.adapters.viewholder.DaysForecastViewHolder;
import com.knr.myweatherapp.util.LargeDataHandler;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Kumar on 26-01-2018.
 */

public class DaysForecastAdapter extends RecyclerView.Adapter<DaysForecastViewHolder> {
    Context mContext;
    public DaysForecastAdapter(Context context){
        this.mContext=context;
    }
    @Override
    public DaysForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horizontal_list_row, parent, false);
        return new DaysForecastViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DaysForecastViewHolder holder, int position) {
        if(LargeDataHandler.getInstance().getForecastList()!=null && LargeDataHandler.getInstance().getForecastList().getList().size()>0){
            holder.place.setText(LargeDataHandler.getInstance().getForecastList().getCity().getName()+","+LargeDataHandler.getInstance().getForecastList().getCity().getCountry());
            float temperature=LargeDataHandler.getInstance().getForecastList().getList().get(position).getMain().getTemp();
            int temp=Math.round((temperature-273.15F));
            int tempInDegree=(int)temp;
            holder.temperature.setText(""+tempInDegree+(char) 0x00B0+"C");
            List<Weather> list=LargeDataHandler.getInstance().getForecastList().getList().get(position).getWeather();
            for(Weather weather:list){
                String desc=weather.getDescription();
                holder.description.setText(desc);
            }

            holder.date.setText(LargeDataHandler.getInstance().getForecastList().getList().get(position).getDtTxt());
            holder.wind.setText("Wind : "+LargeDataHandler.getInstance().getForecastList().getList().get(position).getWind().getSpeed()+"m/s ,"+LargeDataHandler.getInstance().getForecastList().getList().get(position).getWind().getDeg());
            holder.pressure.setText("Pressure : "+LargeDataHandler.getInstance().getForecastList().getList().get(position).getMain().getPressure());
            holder.humidity.setText("Humidity : "+LargeDataHandler.getInstance().getForecastList().getList().get(position).getMain().getHumidity()+"%");
            holder.coordinates.setText("["+LargeDataHandler.getInstance().getForecastList().getCity().getCoord().getLat()+","+LargeDataHandler.getInstance().getForecastList().getCity().getCoord().getLon()+"]");

        }
    }


    @Override
    public int getItemCount() {
        return LargeDataHandler.getInstance().getForecastList().getList().size();
    }
}
