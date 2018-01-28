package com.knr.myweatherapp.ui;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.knr.myweatherapp.R;
import com.knr.myweatherapp.ui.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Kumar on 27-01-2018.
 */

public class TaskActivity extends BaseActivity {
    @BindView(R.id.user_input)Button userInput;
    @BindView(R.id.gps_input)Button gpsInput;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @Override
    protected void initializeDagger() {
        setSupportActionBar(toolbar);
        userInput.setOnClickListener(v -> {
            startActivity(new Intent(TaskActivity.this, MainActivity.class));
            finish();
        });
        gpsInput.setOnClickListener(v -> {
            startActivity(new Intent(TaskActivity.this, GPSLocationActivity.class));
            finish();
        });
    }

    @Override
    protected void initializePresenter() {

    }

    @Override
    public int getLayoutid() {
        return R.layout.task_layout;
    }
}
