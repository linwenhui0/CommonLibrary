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

import com.hlibrary.base.CommonHandler;
import com.hlibrary.util.DensityUtil;
import com.hlibrary.util.ToastUtil;

import java.lang.reflect.Field;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {

    public CommonHandler mActivityHanler;
    protected T mViewDataBind;
    private static int staticPixels = 0;

    public boolean useDataBinding() {
        return true;
    }

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
        if (staticPixels == 0) {
            try {
                Class cls = Class.forName(getPackageName() + ".BuildConfig");
                try {
                    Field field = cls.getField("STATIC_PIXELS");
                    staticPixels = (int) field.get(null);
                } catch (Exception e) {
                    e.printStackTrace();
                    staticPixels = 360;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        DensityUtil.setCustomDensity(this, getApplication(), staticPixels);
        mActivityHanler = CommonHandler.Companion.obtain(this);
        mActivityHanler.pushActivity(this);
        setStatusBarColorResId(getStatusBarColorResId());
        if (useDataBinding()) {
            mViewDataBind = DataBindingUtil.setContentView(this, getLayoutRes());
        } else {
            setContentView(getLayoutRes());
        }

    }

    @Override
    protected void onDestroy() {
        mActivityHanler.pullActivity(this);
        mActivityHanler.release();
        mActivityHanler = null;
        super.onDestroy();
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

    public int getDimensionPixelSize(@DimenRes int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

}
