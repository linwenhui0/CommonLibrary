package com.hlibrary.application;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;

import com.hlibrary.crashmanager.AppCrashHandler;
import com.hlibrary.image.ImageManager;
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

    public abstract boolean isLogDebug();

    public abstract boolean isLogFileDebug();

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onCreate() {
        super.onCreate();
        init();
        Logger.getInstance().setPackageName(this);
        Logger.getInstance().setDebug(isLogDebug());
        Logger.getInstance().setFileDebug(isLogFileDebug());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        activityManager.exit();
        System.exit(0);
    }

    private void init() {
        instance = this;
        AppCrashHandler.getInstance();
        ImageManager.Companion.getInstance(this).init();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;

    }

    public ActivityManager getActivityManager() {
        if (activityManager == null)
            activityManager = new ActivityManager();
        return activityManager;
    }

}
