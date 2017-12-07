package com.hlibrary.base.handler;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pools;

import com.hlibrary.application.AppContext;
import com.hlibrary.entity.MessageEvent;
import com.hlibrary.util.Logger;
import com.hlibrary.util.ToastUtil;

import java.io.Serializable;
import java.util.concurrent.Future;


/**
 * @author linwenhui
 * @date 2015/9/15.
 */
public class CommonHandler implements HandlerInterface<CommonHandler> {

    protected static final String TAG = "CommonHandler";

    private Context context;
    protected LocalBroadcastManager localBroadcastManager;
    private String ExtraTag;
    private ProgressDialog progressDialog;
    private static Pools.SimplePool<CommonHandler> handlePool = new Pools.SimplePool<>(20);

    protected CommonHandler(Context context) {
        init(context);
    }

    protected final void init(Context context) {
        this.context = context;
        localBroadcastManager = LocalBroadcastManager.getInstance(context.getApplicationContext());
    }

    public String getExtraTag() {
        return ExtraTag;
    }

    public void setExtraTag(@NonNull String extraTag) {
        ExtraTag = extraTag;
    }

    public String getTag() {
        Logger.i(TAG, " " + TAG + " " + ExtraTag);
        if (!"".equals(ExtraTag)) {
            return ExtraTag;
        }
        return TAG;
    }

    @Override
    public void showToast(@NonNull String text) {
        ToastUtil.showLongTime(context, text);
    }

    @Override
    public void showToast(@NonNull String text, @IntRange(from = 1) int duration) {
        ToastUtil.showCustomTime(context, text, duration);
    }

    @Override
    public void showToast(@StringRes int resId) {
        ToastUtil.showLongTime(context, resId);
    }

    @Override
    public void showToast(@StringRes int resId, @IntRange(from = 1) int duration) {
        ToastUtil.showCustomTime(context, resId, duration);
    }

    @Override
    public void showProgess(@NonNull String msg) {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(msg);
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            progressDialog.setMessage(msg);
            progressDialog.show();
        }
    }

    @Override
    public void dismissProgress() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public Future runNewThread(@NonNull Runnable r) {
        return AppContext.threadPool.submit(r);
    }

    @Override
    public void runOnUiThread(@NonNull Runnable r) {
        AppContext.mainHandler.post(r);
    }

    @Override
    public void runOnUiThreadForDelay(@NonNull Runnable r, @IntRange(from = 1) long delayMillis) {
        AppContext.mainHandler.postDelayed(r, delayMillis);
    }

    @Override
    public boolean sendBroadcast(@NonNull Intent intent) {
        return localBroadcastManager.sendBroadcast(intent);
    }

    @Override
    public void startActivityForCls(@NonNull Class<? extends Activity> cls) {
        context.startActivity(new Intent(context, cls));
    }

    @Override
    public void startActivityForCls(@NonNull Class<? extends Activity> cls, @NonNull Bundle options) {
        Intent intent = new Intent(context, cls);
        intent.putExtras(options);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    @Override
    public void startActivityForCls(@NonNull Class<? extends Activity> cls, @NonNull String value) {
        Bundle bundle = new Bundle();
        bundle.putString(getTag(), value);
        startActivityForCls(cls, bundle);
    }

    @Override
    public void startActivityForCls(@NonNull Class<? extends Activity> cls, @NonNull String[] values) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(getTag(), values);
        startActivityForCls(cls, bundle);
    }

    @Override
    public void startActivityForCls(@NonNull Class<? extends Activity> cls, @NonNull Serializable serializable) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(getTag(), serializable);
        startActivityForCls(cls, bundle);
    }

    @Override
    public void startActivityForCls(@NonNull Class<? extends Activity> cls, @NonNull Parcelable parcelable) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(getTag(), parcelable);
        startActivityForCls(cls, bundle);
    }

    @Override
    public void startActivityForCls(@NonNull Class<? extends Activity> cls, @NonNull Parcelable[] parcelables) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(getTag(), parcelables);
        startActivityForCls(cls, bundle);
    }

    @Override
    public void postEvent(MessageEvent msgEvent) {
    }

    @Override
    public void register(Object subscriber) {
    }

    @Override
    public void unregister(Object subscriber) {
    }

    public void release() {
        if (handlePool != null)
            handlePool.release(this);
    }

    public static CommonHandler obtain(Context context) {
        CommonHandler commonHandler = handlePool.acquire();
        if (commonHandler != null) {
            commonHandler.init(context);
        } else {
            commonHandler = new CommonHandler(context);
        }
        return commonHandler;
    }


}
