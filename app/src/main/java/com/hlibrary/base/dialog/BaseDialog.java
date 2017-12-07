package com.hlibrary.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.hlibrary.R;
import com.hlibrary.base.handler.CommonHandler;


public abstract class BaseDialog<T extends ViewDataBinding> extends Dialog {

    protected CommonHandler mDialogHanler;
    private LayoutInflater inflater;
    private FrameLayout parent;
    protected T mDataBinding;

    public BaseDialog(Context context) {
        this(context, R.style.DialogStyle);
    }

    public BaseDialog(Context context, @StyleRes int theme) {
        super(context, theme);
        inflater = LayoutInflater.from(context);
        parent = new FrameLayout(context);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        mDataBinding = DataBindingUtil.inflate(inflater, layoutResID, parent, false);
        super.setContentView(mDataBinding.getRoot());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialogHanler = CommonHandler.obtain(getContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mDialogHanler!=null)
            mDialogHanler = CommonHandler.obtain(getContext());
        mDialogHanler.register(this);
    }

    @Override
    public void setContentView(View view) {
        mDataBinding = DataBindingUtil.bind(view);
        super.setContentView(mDataBinding.getRoot());
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        mDataBinding = DataBindingUtil.bind(view);
        super.setContentView(mDataBinding.getRoot(), params);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDialogHanler.unregister(this);
        mDialogHanler.release();
    }

    @Override
    public void show() {
        if (!isShowing()) {
            super.show();
        }
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            super.dismiss();
        }
    }
}
