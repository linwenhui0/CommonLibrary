package com.hlibrary.application

import android.annotation.TargetApi
import android.app.Application
import android.os.Build
import com.hlibrary.image.ImageManager
import com.hlibrary.util.Logger
import com.hlibrary.util.crash.AppCrashHandler
import com.hlibrary.util.crash.ICrashListener
import com.hlibrary.utils.ActivityManager
import kotlin.system.exitProcess

abstract class AppContext : Application(), ICrashListener {
    abstract val isLogDebug: Boolean
    abstract val isLogFileDebug: Boolean
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    override fun onCreate() {
        super.onCreate()
        init()
        Logger.instance.setPackageName(this)
        Logger.instance.setDebug(isLogDebug)
        Logger.instance.setFileDebug(isLogFileDebug)
    }

    override fun onTerminate() {
        super.onTerminate()
        ActivityManager.instance.exit()
        exitProcess(0)
    }

    private fun init() {
        AppCrashHandler.instance.crashListener = this
        ImageManager.getInstance(this).init()
    }

}