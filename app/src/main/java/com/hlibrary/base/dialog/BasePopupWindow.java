package com.hlibrary.base.dialog;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

/**
 * Created by linwenhui on 2015/11/9.
 */
public class BasePopupWindow<T extends ViewDataBinding> extends PopupWindow {

    protected View contentView;
    protected Activity activity;
    protected T mViewBinding;
    protected LayoutInflater inflater;
    protected FrameLayout parent;

    public BasePopupWindow(@NonNull Activity activity) {
        super(activity);
        this.activity = activity;
        inflater = LayoutInflater.from(activity);
        parent = new FrameLayout(activity);
    }

    public void setContentView(@LayoutRes int layoutRes) {
        mViewBinding = DataBindingUtil.inflate(inflater,layoutRes,parent,false);
        contentView = mViewBinding.getRoot();
        super.setContentView(contentView);
        super.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        super.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public View findViewById(@IdRes int id) {
        return contentView.findViewById(id);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    protected void backgroundAlpha(@FloatRange(from = 0,to = 1.0) float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }

}
