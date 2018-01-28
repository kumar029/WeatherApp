package com.knr.myweatherapp.ui;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.knr.myweatherapp.R;
import com.knr.myweatherapp.presenter.WeatherPresenter;
import com.knr.myweatherapp.presenter.interfaces.WeatherContract;
import com.knr.myweatherapp.presenter.model.WeatherResponseModel;
import com.knr.myweatherapp.ui.adapters.ForecastAdapter;
import com.knr.myweatherapp.ui.base.BaseActivity;
import com.knr.myweatherapp.util.App;
import com.knr.myweatherapp.util.LargeDataHandler;
import com.knr.myweatherapp.util.LocationTrack;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Created by Kumar on 27-01-2018.
 */

public class GPSLocationActivity extends BaseActivity implements WeatherContract.View{
    @BindView(R.id.viewpager)ViewPager viewPager;
    @BindView(R.id.try_again_layer)LinearLayout tryAgainLayout;
    @BindView(R.id.progressbar_layer)ProgressBar progressBarLayout;
    @BindView(R.id.toolbar)Toolbar toolbar;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;
    @Inject
    WeatherPresenter presenter;
    private ForecastAdapter forecastAdapter;
    double longitude;
    double latitude;

    @Override
    protected void initializeDagger() {
        App app = (App) getApplicationContext();
        app.getPermissionComponentPreference().inject(GPSLocationActivity.this);
        setSupportActionBar(toolbar);
        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }


        Button btn = (Button) findViewById(R.id.find_location);

        locationTrack = new LocationTrack(GPSLocationActivity.this);
        btn.setOnClickListener(view -> {

            if (locationTrack.canGetLocation()) {


                longitude = locationTrack.getLongitude();
                latitude = locationTrack.getLatitude();
                presenter.onGpsWeatherData(latitude,longitude);

                Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_SHORT).show();
            } else {

                locationTrack.showSettingsAlert();
            }

        });
    }

    @Override
    protected void initializePresenter() {
        super.presenter=presenter;
        presenter.setView(this);
    }

    @Override
    public int getLayoutid() {
        return R.layout.gps_layout;
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(GPSLocationActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
        if(presenter!=null){
            presenter.unSubscribe();
        }
    }

    @Override
    public void onWeatherSuccess(Object model) {
        progressBarLayout.setVisibility(View.GONE);
        tryAgainLayout.setVisibility(View.GONE);
        if(model instanceof WeatherResponseModel){
            LargeDataHandler.getInstance().setForecastList(((WeatherResponseModel) model));
            forecastAdapter=new ForecastAdapter(getSupportFragmentManager());
            viewPager.setAdapter(forecastAdapter);
        }
    }

    @Override
    public void onFail() {
        Toast.makeText(getApplicationContext(),"Data load failed",Toast.LENGTH_SHORT).show();
        progressBarLayout.setVisibility(View.GONE);
        if(viewPager.getChildCount()==0) {

            tryAgainLayout.setVisibility(View.VISIBLE);
        }
    }
    @OnClick(R.id.try_again_layer)
    public void retry(View v)
    {
        progressBarLayout.setVisibility(View.VISIBLE);
        tryAgainLayout.setVisibility(View.GONE);
        if(presenter!=null)
        {
            presenter.onGpsWeatherData(latitude,longitude);
        }
    }
}
