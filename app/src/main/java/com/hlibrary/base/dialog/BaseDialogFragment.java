package com.hlibrary.base.dialog;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.IdRes;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hlibrary.R;
import com.hlibrary.base.CommonHandler;
import com.hlibrary.base.activity.BaseActivity;

/**
 * Created by linwenhui on 2015/10/1.
 */
public class BaseDialogFragment<T extends ViewDataBinding> extends DialogFragment implements View.OnClickListener {

    protected CommonHandler mFragmentHandler;
    protected Bundle savedState;
    protected T mDataBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFragmentHandler = CommonHandler.Companion.obtain(getActivity().getApplicationContext());
        if (!restoreStateFromArguments()) {
            // First Time, Initialize something here
            onFirstTimeLaunched();
        }

    }

    @Override
    public void onDestroyView() {
        mFragmentHandler.release();
        mFragmentHandler = null;
        super.onDestroyView();
    }

    protected View findViewById(@IdRes int id) {
        return getView().findViewById(id);
    }

    protected void onFirstTimeLaunched() {

    }

    protected View setContentView(int layoutResID, LayoutInflater inflater, ViewGroup container) {
        mDataBinding = DataBindingUtil.inflate(inflater, layoutResID, container, false);
        return mDataBinding.getRoot();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveStateToArguments();
    }


    private static final String STATE_TAG = "STATE_TAG";

    private void saveStateToArguments() {
        savedState = saveState();
        if (savedState != null) {
            Bundle b = getArguments();
            if (b == null)
                b = new Bundle();
            b.putBundle(STATE_TAG, savedState);
        }
    }

    private boolean restoreStateFromArguments() {
        Bundle b = getArguments();
        if (b != null) {
            if (b.containsKey(STATE_TAG)) {
                savedState = b.getBundle(STATE_TAG);
                if (savedState != null) {
                    restoreState();
                    return true;
                }
            }
        }
        return false;
    }

    private void restoreState() {
        if (savedState != null) {
            // For Example
            //tv1.setText(savedState.getString(text));
            onRestoreState(savedState);
        }
    }

    protected void onRestoreState(Bundle savedInstanceState) {

    }

    private Bundle saveState() {
        Bundle state = new Bundle();
        // For Example
        //state.putString(text, tv1.getText().toString());
        onSaveState(state);
        return state;
    }

    protected void onSaveState(Bundle outState) {

    }

    @Override
    public void onClick(View v) {

    }

    public int getDimensionPixelSize(@DimenRes int id) {
        return getResources().getDimensionPixelSize(id);
    }

    private BaseActivity activity;

    public BaseActivity getBaseActivity() {
        if (activity == null && getActivity() instanceof BaseActivity)
            activity = (BaseActivity) getActivity();
        return activity;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (!isAdded())
            transaction.remove(this);
        super.show(manager, tag);

    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
