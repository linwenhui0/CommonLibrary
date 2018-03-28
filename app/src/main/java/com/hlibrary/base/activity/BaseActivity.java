package com.hlibrary.base.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.hlibrary.base.handler.ActivityHandler;
import com.hlibrary.db.DatabaseHelper;
import com.hlibrary.entity.MessageEvent;
import com.hlibrary.util.ToastUtil;
import com.j256.ormlite.android.apptools.OpenHelperManager;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {


    public ActivityHandler mActivityHanler;
    private DatabaseHelper databaseHelper = null;
    protected T mViewDataBind;

    public abstract int getLayoutRes();

    public int getStatusBarColorResId() {
        return android.R.color.black;
    }

    public void setStatusBarColorResId(@ColorRes int colorResId) {
        setStatusBarColor(ContextCompat.getColor(this, colorResId));
    }

    public void setStatusBarColor(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (color == Color.TRANSPARENT) {
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    protected void setWindowBackgroundResId(@ColorRes int backgroundResId) {
        getWindow().setBackgroundDrawableResource(backgroundResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityHanler = ActivityHandler.obtain(this);
        mActivityHanler.pushActivity(this);
        setStatusBarColorResId(getStatusBarColorResId());
        mViewDataBind = DataBindingUtil.setContentView(this, getLayoutRes());
        openDB();
    }

    @Deprecated
    @Override
    public void setContentView(View view) {
    }

    @Deprecated
    @Override
    public void setContentView(int layoutResID) {
    }

    @Deprecated
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {

    }

    @Override
    protected final void onResume() {
        super.onResume();
        mActivityHanler.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mActivityHanler.unregister(this);
    }

    @Override
    protected void onDestroy() {
        mActivityHanler.pullActivity(this);
        mActivityHanler.release();
        mActivityHanler = null;
        closeDB();
        super.onDestroy();
    }

    protected Class<? extends DatabaseHelper> getDatabaseClass() {
        return null;
    }

    private final void openDB() {
        if (getDatabaseClass() == null)
            return;

        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(this, getDatabaseClass());
        }

    }

    private final void closeDB() {
        if (getDatabaseClass() == null)
            return;

        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }

    }

    public final void setupToolbar(Toolbar toolbar) {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    public void showToast(@NonNull String text) {
        ToastUtil.showLongTime(this, text);
    }

    public void showToast(@NonNull String text, @IntRange(from = 1) int duration) {
        ToastUtil.showCustomTime(this, text, duration);
    }

    public void showToast(@StringRes int resId) {
        ToastUtil.showLongTime(this, resId);
    }

    public void showToast(@StringRes int resId, @IntRange(from = 1) int duration) {
        ToastUtil.showCustomTime(this, resId, duration);
    }

    public void showProgess(@NonNull String msg) {
        mActivityHanler.showProgess(msg);
    }

    public void dismissProgress() {
        mActivityHanler.dismissProgress();
    }

    protected boolean parseMessageEvent(MessageEvent msgEvent, int type) {
        if (msgEvent != null && msgEvent.getType() == type)
            return true;
        return false;
    }

    protected boolean parseMessageEvent(@NonNull MessageEvent msgEvent, @NonNull String key) {
        return key.equals(msgEvent.getKey());
    }

    public int getDimensionPixelSize(@DimenRes int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

}
