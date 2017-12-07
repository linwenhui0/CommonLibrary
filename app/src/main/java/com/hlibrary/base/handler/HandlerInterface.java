package com.hlibrary.base.handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.hlibrary.entity.MessageEvent;

import java.io.Serializable;
import java.util.concurrent.Future;

/**
 * @author linwenhui
 * @date 2015/9/15.
 */
public interface HandlerInterface<T> {

    void showToast(String text);

    void showToast(String text, int duration);

    void showToast(int resId);

    void showToast(int resId, int duration);

    void showProgess(String msg);

    void dismissProgress();

    Future runNewThread(Runnable r);

    void runOnUiThread(Runnable r);

    void runOnUiThreadForDelay(Runnable r, long delayMillis);

    boolean sendBroadcast(Intent intent);

    void startActivityForCls(Class<? extends Activity> cls);

    void startActivityForCls(Class<? extends Activity> cls, Bundle options);

    void startActivityForCls(Class<? extends Activity> cls, String value);

    void startActivityForCls(Class<? extends Activity> cls, String[] values);

    void startActivityForCls(Class<? extends Activity> cls, Serializable serializable);

    void startActivityForCls(Class<? extends Activity> cls, Parcelable parcelable);

    void startActivityForCls(Class<? extends Activity> cls, Parcelable[] parcelables);

    void postEvent(MessageEvent msgEvent);

    void register(Object subscriber);

    void unregister(Object subscriber);

}
