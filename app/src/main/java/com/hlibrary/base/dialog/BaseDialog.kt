package com.hlibrary.base.dialog

import android.app.Dialog
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
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
        super.setContentView(mDataBinding!!.root)
    }

    override fun setContentView(view: View) {
        mDataBinding = DataBindingUtil.bind<T>(view)
        super.setContentView(mDataBinding!!.root)
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        mDataBinding = DataBindingUtil.bind<T>(view)
        super.setContentView(mDataBinding!!.root, params)
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