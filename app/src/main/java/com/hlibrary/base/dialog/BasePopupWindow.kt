package com.hlibrary.base.dialog

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.FloatRange
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.PopupWindow

/**
 *
 * @author linwenhui
 * @date 2015/11/9
 */
open class BasePopupWindow<T : ViewDataBinding?>(protected val activity: Activity) : PopupWindow(activity) {

    protected var mViewBinding: T? = null

    private val inflater: LayoutInflater

    protected var parent: FrameLayout

    fun setContentView(@LayoutRes layoutRes: Int) {
        mViewBinding = DataBindingUtil.inflate<T>(inflater, layoutRes, parent, false)
        contentView = mViewBinding?.root
        super.setContentView(contentView)
        super.setWidth(ViewGroup.LayoutParams.MATCH_PARENT)
        super.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun findViewById(@IdRes id: Int): View? {
        return contentView?.findViewById(id)
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    protected fun backgroundAlpha(bgAlpha: Float) {
        val lp = activity.window.attributes
        lp?.alpha = bgAlpha
        activity.window.attributes = lp
    }

    init {
        inflater = LayoutInflater.from(activity)
        parent = FrameLayout(activity)
    }
}