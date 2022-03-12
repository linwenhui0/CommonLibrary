package com.hlibrary.application

import android.app.Application
import android.os.Build
import com.hlibrary.image.ImageManager
import com.hlibrary.utils.ActivityManager
import kotlin.system.exitProcess

open class AppContext : Application() {

    override fun onCreate() {
        super.onCreate()
        ImageManager.getInstance(this).init()
    }

    override fun onTerminate() {
        super.onTerminate()
        ActivityManager.instance.exit()
        exitProcess(0)
    }

}