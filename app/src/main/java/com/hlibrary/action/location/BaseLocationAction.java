package com.hlibrary.action.location;


import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.Context;
import android.support.v4.app.SupportActivity;

import com.hlibrary.action.listener.LocationListener;

/**
 * Created by linwenhui on 2015/10/1.
 */
public abstract class BaseLocationAction implements LifecycleObserver {

    protected boolean locationOnce = true;
    protected LocationListener locationListener;
    private SupportActivity activity;

    public BaseLocationAction(Context context) {
        if (context instanceof SupportActivity) {
            activity = (SupportActivity) context;
            activity.getLifecycle().addObserver(this);
        }
    }

    public boolean isLocationOnce() {
        return locationOnce;
    }

    /**
     * @param locationOnce true 定位一次
     */
    public void setLocationOnce(boolean locationOnce) {
        this.locationOnce = locationOnce;
    }

    public void setLocationListener(LocationListener locationListener) {
        this.locationListener = locationListener;
    }


    /**
     * @return 开始定位
     */
    public abstract void locationStart();

    /**
     * @return 停止定位
     */
    public abstract void locationStop();

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestory() {
        if (activity != null) {
            activity.getLifecycle().removeObserver(this);
        }
    }

}
