package com.hlibrary.utils

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.StringRes
import com.hlibrary.R

class ToastManager private constructor() {

    companion object {
        val instance: ToastManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            ToastManager()
        }
        private var toast: Toast? = null

    }

    @Synchronized
    fun createView(context: Context) {
        toast = Toast(context)
        val v = LayoutInflater.from(context).inflate(R.layout.view_toast, null)
        toast?.view = v
        toast?.setGravity(Gravity.CENTER, 0, 0)
    }

    fun showLongToast(context: Context, text: String) {
        toast?.cancel()
        createView(context)
        toast?.setText(text)
        toast?.duration = Toast.LENGTH_LONG
        toast?.show()
    }

    fun showLongToast(context: Context, @StringRes resId: Int) {
        toast?.cancel()
        createView(context)
        toast?.setText(resId)
        toast?.duration = Toast.LENGTH_LONG
        toast?.show()
    }

    fun showShortToast(context: Context, text: String) {
        toast?.cancel()
        createView(context)
        toast?.setText(text)
        toast?.duration = Toast.LENGTH_SHORT
        toast?.show()
    }

    fun showShortToast(context: Context, @StringRes resId: Int) {
        toast?.cancel()
        createView(context)
        toast?.setText(resId)
        toast?.duration = Toast.LENGTH_SHORT
        toast?.show()
    }


}