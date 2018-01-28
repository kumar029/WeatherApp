
package com.knr.myweatherapp.presenter.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WeatherResponseModel {

    @SerializedName("list")
    @Expose
    private java.util.List<DaysList> list = null;
    @SerializedName("city")
    @Expose
    private City city;

    public java.util.List<DaysList> getList() {
        return list;
    }

    public void setList(java.util.List<DaysList> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }


}
