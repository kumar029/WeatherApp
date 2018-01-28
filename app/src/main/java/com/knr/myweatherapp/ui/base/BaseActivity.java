package com.knr.myweatherapp.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.knr.myweatherapp.presenter.base.Presenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Kumar on 26-01-2018.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog progressDialog;
    protected abstract void initializeDagger();
    protected abstract void initializePresenter();
    protected Presenter presenter;
    private Unbinder unbinder;

    public abstract int getLayoutid();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutid());
        unbinder= ButterKnife.bind(this);
        initializeDagger();
        initializePresenter();

        if (presenter != null) {
            presenter.initialize(getIntent().getExtras());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(presenter!=null){
            presenter.start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(presenter!=null){
            presenter.finalizeView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
    public void showProgressDialog(String message){
        if(progressDialog==null || !progressDialog.isShowing()){
            progressDialog=new ProgressDialog(this);
            progressDialog.setMessage(message);
            progressDialog.setCanceledOnTouchOutside(false);
            if(!isFinishing()){
                progressDialog.show();
            }
        }

    }
    protected void hideProgressDialog(){
        if(progressDialog!=null && progressDialog.isShowing()){
            if(!isFinishing()){
                progressDialog.dismiss();
            }
        }

    }
}
