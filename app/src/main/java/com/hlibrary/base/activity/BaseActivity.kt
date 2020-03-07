package com.hlibrary.base.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hlibrary.utils.ActivityManager
import com.hlibrary.utils.ToastManager

abstract class BaseActivity<T : ViewDataBinding?> : AppCompatActivity() {
    protected var mViewDataBind: T? = null
    abstract val layoutResource: Int
    var statusBarColorResId: Int
        get() = 0
        set(colorResId) {
            setStatusBarColor(ContextCompat.getColor(this, colorResId))
        }

    fun setStatusBarColor(@ColorInt color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = color
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (color == Color.TRANSPARENT) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    protected fun setWindowBackgroundResId(@ColorRes backgroundResId: Int) {
        window.setBackgroundDrawableResource(backgroundResId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityManager.instance.pushActivity(this)
        val statusBarColor = statusBarColorResId
        if (statusBarColor > 0) {
            statusBarColorResId = statusBarColor
        }
        mViewDataBind = DataBindingUtil.setContentView<T>(this, layoutResource)
    }

    override fun onDestroy() {
        ActivityManager.instance.popActivity(this)
        super.onDestroy()
    }

    fun setupToolbar(toolbar: Toolbar) {
        toolbar.title = ""
        setSupportActionBar(toolbar)
    }

    fun showLongToast(text: String) {
        ToastManager.instance.showLongToast(this, text)
    }

    fun showShortToast(text: String) {
        ToastManager.instance.showShortToast(this, text)
    }

    fun showLongToast(@StringRes resId: Int) {
        ToastManager.instance.showLongToast(this, resId)
    }

    fun showShortToast(@StringRes resId: Int) {
        ToastManager.instance.showShortToast(this, resId)
    }
}