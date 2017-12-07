package com.hlibrary.application;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;

import com.hlibrary.crashmanager.AppCrashHandler;
import com.hlibrary.image.ImageUtil;
import com.hlibrary.util.Logger;
import com.hlibrary.utils.ActivityManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AppContext extends Application {

    public final static Handler mainHandler = new Handler(Looper.getMainLooper());
    public static ExecutorService threadPool = Executors.newScheduledThreadPool(3);

    public static AppContext instance;
    private ActivityManager activityManager;

    public int screenWidth, screenHeight;
    public static double ITEM_WIDTH, ITEM_HEIGHT;//1dp代表的px

    public abstract boolean isLogDebug();

    public abstract boolean isLogFileDebug();

    public abstract int getStaticWidth();

    public abstract int getStaticHeight();

    public boolean isAutoFitView() {
        return true;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onCreate() {
        super.onCreate();
        init();
        ImageUtil.initImageLoader(this);
        Logger.setPackageName(this);
        Logger.setDEBUG(isLogDebug(), isLogFileDebug());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ImageUtil.clearMemory();
        activityManager.exit();
        System.exit(0);
    }

    private void init() {
        instance = this;
        AppCrashHandler.getInstance();
        ImageUtil.initImageLoader(instance);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;
        ITEM_HEIGHT = 1.0 * screenHeight / getStaticHeight();
        ITEM_WIDTH = 1.0 * screenWidth / getStaticWidth();

    }

    public ActivityManager getActivityManager() {
        if (activityManager == null)
            activityManager = new ActivityManager();
        return activityManager;
    }

}
