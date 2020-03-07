package com.hlibrary.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.hlibrary.R
import com.hlibrary.base.ITabUpdate
import com.hlibrary.base.activity.BaseActivity

open class BaseFragment<T : ViewDataBinding?> : Fragment() {

    protected var mViewDataBind: T? = null
    protected var savedState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        onLocalActivityCreated(savedInstanceState)
        super.onActivityCreated(savedInstanceState)
        if (!restoreStateFromArguments()) { // First Time, Initialize something here
            onFirstTimeLaunched()
        }
    }

    fun onLocalActivityCreated(savedInstanceState: Bundle?) {}


    protected fun onFirstTimeLaunched() {}

    protected fun setContentView(@LayoutRes layoutResID: Int, inflater: LayoutInflater, container: ViewGroup): View? {
        mViewDataBind = DataBindingUtil.inflate<T>(inflater, layoutResID, container, false)
        return mViewDataBind?.root
    }

    protected fun setupToolbar(toolbar: Toolbar) {
        val baseActivity = activity as BaseActivity<*>?
        baseActivity?.setupToolbar(toolbar)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveStateToArguments(outState)
    }

    private fun saveStateToArguments(outState: Bundle) {
        savedState = saveState()
        if (savedState != null) {
            var b = arguments
            if (b == null) {
                b = Bundle()
            }
            b.putBundle(STATE_TAG, savedState)
        }
    }

    private fun restoreStateFromArguments(): Boolean {
        val b = arguments
        if (b != null) {
            if (b.containsKey(STATE_TAG)) {
                savedState = b.getBundle(STATE_TAG)
                if (savedState != null) {
                    restoreState()
                    return true
                }
            }
        }
        return false
    }

    private fun restoreState() {
        if (savedState != null) {
            onRestoreState(savedState)
        }
    }

    protected fun onRestoreState(savedInstanceState: Bundle?) {}

    private fun saveState(): Bundle {
        val state = Bundle()
        onSaveState(state)
        return state
    }

    protected fun onSaveState(outState: Bundle?) {}



    private var tabUpdate: ITabUpdate? = null

    fun onUpdateTab(index: Int) {
        if (tabUpdate != null) {
            tabUpdate?.onUpdateTab(index)
        } else {
            val activity = activity
            if (activity is ITabUpdate) {
                tabUpdate = activity
                tabUpdate?.onUpdateTab(index)
            }
        }
    }

    companion object {
        private val CONTENT_ID = R.id.frmlyt_tab
        private const val STATE_TAG = "STATE_TAG"
        fun <T : BaseFragment<*>?> getFragment(
                fragmentManager: FragmentManager, cls: Class<T>): T? {
            return fragmentManager.findFragmentByTag(cls.name) as T?
        }

        fun isAdd(id: Int, fragmentTransaction: FragmentTransaction,
                  fragment: Fragment) {
            if (!fragment.isAdded) {
                fragmentTransaction
                        .add(id, fragment, fragment.javaClass.name)
                fragmentTransaction.hide(fragment)
            }
        }

        private var curKey: String? = null

        fun showAndHideFragment(id: Int,
                                fragmentManager: FragmentManager, fragment: Fragment) {
            val fragmentTransaction = fragmentManager
                    .beginTransaction()
            if (curKey != null) {
                val curFragment = fragmentManager.findFragmentByTag(curKey)
                if (curFragment != null) {
                    fragmentTransaction.hide(curFragment)
                }
            }
            curKey = fragment.javaClass.name
            if (fragment.isAdded) {
                fragmentTransaction.show(fragment)
            } else {
                fragmentTransaction.add(id, fragment, curKey)
            }
            if (!fragmentTransaction.isEmpty) {
                fragmentTransaction.commit()
                fragmentManager.executePendingTransactions()
            }
        }

        fun showAndHideFragment(fragmentManager: FragmentManager, fragment: Fragment) {
            showAndHideFragment(CONTENT_ID, fragmentManager, fragment)
        }

        fun showFragment(id: Int, fragmentManager: FragmentManager,
                         fragment: Fragment?) {
            val fragmentTransaction = fragmentManager
                    .beginTransaction()
            fragmentTransaction.replace(id, fragment!!)
            if (!fragmentTransaction.isEmpty) {
                fragmentTransaction.commit()
                fragmentManager.executePendingTransactions()
            }
        }

        fun showFragment(fragmentManager: FragmentManager, fragment: Fragment?) {
            showFragment(CONTENT_ID, fragmentManager, fragment)
        }
    }
}