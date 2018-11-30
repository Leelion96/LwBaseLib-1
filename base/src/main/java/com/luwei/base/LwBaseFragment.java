package com.luwei.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luwei.base.bus.RxBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class LwBaseFragment<P extends IPresent>
        extends Fragment implements IView<P> {
    protected AppCompatActivity hostActivity;
    private P p;
    private Unbinder unbinder;
    private View mRootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.mRootView = inflater.inflate(this.getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this,mRootView);
        initView(savedInstanceState);
        initEvent();
        initData();
        return mRootView;
    }


    @Override
    public P getP() {
        return p;
    }

    @Override
    public void onDestroy() {
        RxBus.getInstance().unregister(this);
        if (getP() != null) {
            getP().detachV();
        }
        p = null;
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.hostActivity = (AppCompatActivity) activity;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.hostActivity = (AppCompatActivity) context;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        hostActivity = null;
    }

}