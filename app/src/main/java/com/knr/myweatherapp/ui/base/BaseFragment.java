package com.knr.myweatherapp.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.knr.myweatherapp.presenter.base.BaseView;
import com.knr.myweatherapp.presenter.base.Presenter;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Kumar on 26-01-2018.
 */

public abstract class BaseFragment extends Fragment implements BaseView {
    protected FragmentManager fragmentManager;

    protected Presenter presenter;

    protected abstract void initializeDagger();

    protected abstract void initializePresenter();

    protected abstract void initializeActivity();

    public abstract int getLayoutId();

    private View view;

    private Unbinder unbinder;
    protected ProgressDialog progressDialog;


//    private String toolbarTitleKey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getActivity().getSupportFragmentManager();
        // Retain this fragment across configuration changes.
        setRetainInstance(true);
        initializeDagger();
        initializePresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);

        if (presenter != null) {

            presenter.initialize(getArguments());

        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeActivity();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) {
            presenter.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) {
            presenter.finalizeView();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //    public void setTitle(String title) {
//        final ActionBar actionBar = ((BaseActivity) getActivity()).getSupportActionBar();
//        if (actionBar != null) {
//            TextView titleTextView = ButterKnife.findById(getActivity(), R.id.txt_toolbar_title);
//            if (TextUtils.isEmpty(title)) {
//                titleTextView.setText(title);
//            }
//        }
//    }
    protected void showProgressDialog(String message) {


        if (progressDialog == null || !progressDialog.isShowing()) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage(message);
            progressDialog.setCanceledOnTouchOutside(false);
            if (isAdded()) {
                progressDialog.show();
            }
        }

    }

    protected void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            if (isAdded()) {
                progressDialog.dismiss();
            }
        }
    }
}
