package com.hlibrary.base.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hlibrary.R;
import com.hlibrary.base.CommonHandler;
import com.hlibrary.base.ITabUpdate;
import com.hlibrary.base.activity.BaseActivity;

public class BaseFragment<T extends ViewDataBinding> extends Fragment {

    private static final int CONTENT_ID = R.id.frmlyt_tab;

    protected CommonHandler mFragmentHandler;
    protected T mViewDataBind;
    protected Bundle savedState;

    protected String title = "BaseFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public final void onActivityCreated(Bundle savedInstanceState) {
        onLocalActivityCreated(savedInstanceState);
        super.onActivityCreated(savedInstanceState);
        mFragmentHandler = CommonHandler.Companion.obtain(getActivity());
        if (!restoreStateFromArguments()) {
            // First Time, Initialize something here
            onFirstTimeLaunched();
        }

    }

    @Override
    public void onDestroy() {
        mFragmentHandler.release();
        super.onDestroy();
    }

    public void onLocalActivityCreated(Bundle savedInstanceState) {
    }

    protected View findViewById(int id) {
        return getView().findViewById(id);
    }

    protected void onFirstTimeLaunched() {

    }

    protected final View setContentView(@LayoutRes int layoutResID, @NonNull LayoutInflater inflater, @NonNull
            ViewGroup container) {
        mViewDataBind = DataBindingUtil.inflate(inflater, layoutResID, container, false);
        return mViewDataBind.getRoot();
    }

    protected void onBtnLeftClick(View v) {
    }

    protected void onBtnRightClick(View v) {
    }

    protected void setupToolbar(Toolbar toolbar) {
        BaseActivity baseActivity = (BaseActivity) getActivity();
        baseActivity.setupToolbar(toolbar);
    }

    @Override
    public final void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveStateToArguments(outState);
    }

    private static final String STATE_TAG = "STATE_TAG";

    private void saveStateToArguments(Bundle outState) {
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

    public static <T extends BaseFragment> T getFragment(
            FragmentManager fragmentManager, Class<T> cls) {
        @SuppressWarnings("unchecked")
        T fragment = (T) fragmentManager.findFragmentByTag(cls.getName());
        return fragment;
    }

    public static void isAdd(int id, FragmentTransaction fragmentTransaction,
                             Fragment fragment) {
        if (!fragment.isAdded()) {
            fragmentTransaction
                    .add(id, fragment, fragment.getClass().getName());
            fragmentTransaction.hide(fragment);
        }
    }

    private static String curKey;

    public static void showAndHideFragment(int id,
                                           FragmentManager fragmentManager, Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        if (curKey != null) {
            Fragment curFragment = fragmentManager.findFragmentByTag(curKey);
            if (curFragment != null)
                fragmentTransaction.hide(curFragment);
        }
        curKey = fragment.getClass().getName();
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(id, fragment, curKey);
        }
        if (!fragmentTransaction.isEmpty()) {
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
        }
    }

    public static void showAndHideFragment(FragmentManager fragmentManager, Fragment fragment) {
        showAndHideFragment(CONTENT_ID, fragmentManager, fragment);
    }

    public static void showFragment(int id, FragmentManager fragmentManager,
                                    Fragment fragment) {
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();
        fragmentTransaction.replace(id, fragment);
        if (!fragmentTransaction.isEmpty()) {
            fragmentTransaction.commit();
            fragmentManager.executePendingTransactions();
        }
    }

    public static void showFragment(FragmentManager fragmentManager, Fragment fragment) {
        showFragment(CONTENT_ID, fragmentManager, fragment);
    }

    public void onClick(View v) {
    }

    public int getColor(int resId) {
        return getResources().getColor(resId);
    }

    public int getDimension(int id) {
        return getResources().getDimensionPixelSize(id);
    }

    public String getTitle() {
        return title;
    }

    private ITabUpdate tabUpdate;

    public void onUpdateTab(int index) {
        if (tabUpdate != null) {
            tabUpdate.onUpdateTab(index);
        } else {
            FragmentActivity activity = getActivity();
            if (activity instanceof ITabUpdate) {
                tabUpdate = (ITabUpdate) activity;
                tabUpdate.onUpdateTab(index);
            }
        }
    }

}
