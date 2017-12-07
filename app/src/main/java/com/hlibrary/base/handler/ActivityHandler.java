package com.hlibrary.base.handler;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.util.Pools;

import com.hlibrary.application.AppContext;
import com.hlibrary.base.activity.BaseActivity;

import java.io.Serializable;

/**
 * Created by linwenhui on 2015/9/15.
 */
public class ActivityHandler extends CommonHandler {

    private Activity activity;
    private static Pools.SimplePool<ActivityHandler> handlePool = new Pools.SimplePool<>(20);

    protected ActivityHandler(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    protected void init(Activity activity) {
        super.init(activity);
        this.activity = activity;
    }

    public void pushActivity(BaseActivity activity) {
        AppContext.instance.getActivityManager().pushActivity(activity);
    }

    public void pullActivity(BaseActivity activity) {
        AppContext.instance.getActivityManager().pullActivity(activity);
    }

    @Override
    public void runOnUiThread(Runnable r) {
        activity.runOnUiThread(r);
    }

    public void registerReceiver(BroadcastReceiver receiver, String... actions) {
        if (receiver == null || actions == null)
            return;

        IntentFilter filter = new IntentFilter();
        for (String action : actions) {
            filter.addAction(action);
        }
        localBroadcastManager.registerReceiver(receiver, filter);
    }

    public void unregisterReceiver(BroadcastReceiver r) {
        if (r == null)
            return;
        localBroadcastManager.unregisterReceiver(r);
    }

    public String getStringExtra() {
        return activity.getIntent().getStringExtra(getTag());
    }

    public String[] getStringArrayExtra() {
        return activity.getIntent().getStringArrayExtra(getTag());
    }

    public Serializable getSerializableExtra() {
        return activity.getIntent().getSerializableExtra(getTag());
    }

    public Parcelable getParcelableExtra() {
        return activity.getIntent().getParcelableExtra(getTag());
    }

    public Parcelable[] getParcelableArrayExtra() {
        return activity.getIntent().getParcelableArrayExtra(getTag());
    }

    public Bundle getBundle() {
        return activity.getIntent().getExtras();
    }

    public void setResult(Bundle bundle) {
        Intent intent = new Intent();
        intent.putExtras(bundle);
        activity.setResult(Activity.RESULT_OK, intent);
    }


    public void startActivityForCls(@NonNull Class<? extends Activity> cls, int req) {
        activity.startActivityForResult(new Intent(activity, cls), req);
    }

    public void startActivityForCls(@NonNull Class<? extends Activity> cls, @NonNull Bundle options, int req) {
        Intent intent = new Intent(activity, cls);
        intent.putExtras(options);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivityForResult(intent, req);
    }

    public void startActivityForCls(@NonNull Class<? extends Activity> cls, @NonNull String value, int req) {
        Bundle bundle = new Bundle();
        bundle.putString(getTag(), value);
        startActivityForCls(cls, bundle, req);
    }

    public void startActivityForCls(@NonNull Class<? extends Activity> cls, @NonNull String[] values, int req) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(getTag(), values);
        startActivityForCls(cls, bundle, req);
    }

    public void startActivityForCls(@NonNull Class<? extends Activity> cls, @NonNull Serializable serializable, int req) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(getTag(), serializable);
        startActivityForCls(cls, bundle, req);
    }

    public void startActivityForCls(@NonNull Class<? extends Activity> cls, @NonNull Parcelable parcelable, int req) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(getTag(), parcelable);
        startActivityForCls(cls, bundle, req);
    }

    public void startActivityForCls(@NonNull Class<? extends Activity> cls, @NonNull Parcelable[] parcelables, int req) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArray(getTag(), parcelables);
        startActivityForCls(cls, bundle, req);
    }

    public static ActivityHandler obtain(Activity context) {
        ActivityHandler commonHandler = handlePool.acquire();
        if (commonHandler != null) {
            commonHandler.init(context);
        } else {
            commonHandler = new ActivityHandler(context);
        }
        return commonHandler;
    }

    public void release() {
        if (handlePool != null) {
            handlePool.release(this);
        }
    }
}
