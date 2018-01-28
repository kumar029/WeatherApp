package com.knr.myweatherapp.ui;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements WeatherContract.View{
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.enter_city)EditText editText;
    @BindView(R.id.usernameWrapper)TextInputLayout mInputCity;
    @BindView(R.id.button)
    Button mCityButton;
    @BindView(R.id.viewpager)ViewPager viewPager;
    @BindView(R.id.try_again_layer)LinearLayout tryAgainLayout;
    @BindView(R.id.progressbar_layer)ProgressBar progressBarLayout;
    @Inject
    WeatherPresenter presenter;
    String cityName;
    private ForecastAdapter forecastAdapter;

    @Override
    protected void initializeDagger() {
        App app = (App) getApplicationContext();
        app.getPermissionComponentPreference().inject(MainActivity.this);

        setSupportActionBar(toolbar);

        editText.addTextChangedListener(new MyTextWatcher(editText));
        mCityButton.setOnClickListener(v -> {
            if (!validateForm()) return;

            presenter.onWeatherData(cityName);

        });


    }

    @Override
    protected void initializePresenter() {
        super.presenter=presenter;
        presenter.setView(this);

    }

    @Override
    public int getLayoutid() {
        return R.layout.activity_main;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            presenter.unSubscribe();
            LargeDataHandler.getInstance().clearWeatherData();
        }
    }
    @OnClick(R.id.try_again_layer)
    public void retry(View v)
    {
        progressBarLayout.setVisibility(View.VISIBLE);
        tryAgainLayout.setVisibility(View.GONE);
        if(presenter!=null)
        {
            presenter.onWeatherData(cityName);
        }
    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged
                (CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {

                case R.id.enter_city:
                    //validateCity();
                    break;

            }
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public boolean validateForm() {

        return validateCity();

    }

    private boolean validateCity() {
        cityName = editText.getText().toString().trim();


        if (cityName.isEmpty() || !isValidCityName(cityName)) {

            mInputCity.setError(getString(R.string.err_msg_email));
            //requestFocus(mEmail);
            return false;
        } else {
            mInputCity.setErrorEnabled(false);
        }

        return true;
    }


    private static boolean isValidCityName(String city) {
        return !TextUtils.isEmpty(city) ;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
