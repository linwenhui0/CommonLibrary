package com.hlibrary.base.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hlibrary.R

/**
 * @author linwenhui
 */
open class BaseDialog<T : ViewDataBinding?> constructor(context: Context, @StyleRes theme: Int = R.style.DialogStyle) : Dialog(context, theme) {

    protected var mDataBinding: T? = null


    override fun setContentView(@LayoutRes layoutResID: Int) {
        val inflater = LayoutInflater.from(context)
        val parent = FrameLayout(context)
        mDataBinding = DataBindingUtil.inflate<T>(inflater, layoutResID, parent, false)
        val v = mDataBinding?.root
        if (v != null) {
            super.setContentView(v)
        } else {
            super.setContentView(layoutResID)
        }
    }

    override fun setContentView(view: View) {
        mDataBinding = DataBindingUtil.bind<T>(view)
        val v = mDataBinding?.root
        if (v != null) {
            super.setContentView(v)
        } else {
            super.setContentView(view)
        }
    }


    override fun setContentView(view: View, params: ViewGroup.LayoutParams?) {
        mDataBinding = DataBindingUtil.bind<T>(view)
        val v = mDataBinding?.root
        if (v != null) {
            super.setContentView(v, params)
        } else {
            super.setContentView(view, params)
        }
    }

    override fun show() {
        if (!isShowing) {
            super.show()
        }
    }

    override fun dismiss() {
        if (isShowing) {
            super.dismiss()
        }
    }


}