package com.hlibrary.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.IntRange
import android.support.annotation.NonNull
import android.support.annotation.StringRes
import android.support.v4.content.LocalBroadcastManager
import android.support.v4.util.Pools
import android.text.TextUtils
import com.hlibrary.application.AppContext
import com.hlibrary.base.activity.BaseActivity
import com.hlibrary.util.Logger
import com.hlibrary.util.ToastUtil
import java.io.Serializable
import java.util.concurrent.Future

class CommonHandler {

    companion object {
        private val TAG = "CommonHandler"
        private val handlePool: Pools.SimplePool<CommonHandler> = Pools.SimplePool(100)
        private val defaultReqCode = -100;

        fun obtain(context: Context): CommonHandler {
            var commonHandler = handlePool.acquire()
            if (commonHandler != null) {
                commonHandler._init(context)
            } else {
                commonHandler = CommonHandler(context)
            }
            return commonHandler
        }
    }

    private var localBroadcastManager: LocalBroadcastManager? = null
    private var context: Context? = null
    var ExtraTag: String? = null
    private var progressDialog: ProgressDialog? = null

    constructor(context: Context) {
        _init(context)
    }

    private fun _init(context: Context): CommonHandler {
        this.context = context
        return this
    }

    fun pushActivity(activity: BaseActivity<*>) {
        AppContext.instance.activityManager.pushActivity(activity)
    }

    fun pullActivity(activity: BaseActivity<*>) {
        AppContext.instance.activityManager.pullActivity(activity)
    }

    fun getTag(): String {
        Logger.getInstance().i(TAG, " $TAG $ExtraTag")
        return if (!TextUtils.isEmpty(ExtraTag)) {
            ExtraTag!!
        } else TAG
    }

    fun showToast(text: String) {
        ToastUtil.showLongTime(context!!, text)
    }

    fun showToast(text: String, @IntRange(from = 1) duration: Int) {
        ToastUtil.showCustomTime(context!!, text, duration)
    }

    fun showToast(@StringRes resId: Int) {
        ToastUtil.showLongTime(context!!, resId)
    }

    fun showToast(@StringRes resId: Int, @IntRange(from = 1) duration: Int) {
        ToastUtil.showCustomTime(context!!, resId, duration)
    }


    fun showProgess(msg: String) {
        if (null == progressDialog) {
            progressDialog = ProgressDialog(context)
            progressDialog?.setMessage(msg)
            progressDialog?.setCancelable(false)
            progressDialog?.show()
        } else {
            progressDialog?.setMessage(msg)
            progressDialog?.show()
        }
    }


    fun dismissProgress() {
        if (progressDialog != null && progressDialog?.isShowing()!!)
            progressDialog?.dismiss()
    }


    fun runNewThread(r: Runnable): Future<*> {
        return AppContext.threadPool.submit(r)
    }


    fun runOnUiThread(r: Runnable) {
        if (context is BaseActivity<*>) {
            (context as BaseActivity<*>).runOnUiThread(r)
        } else {
            AppContext.mainHandler.post(r)
        }
    }


    fun runOnUiThreadForDelay(r: Runnable, @IntRange(from = 1) delayMillis: Long) {
        AppContext.mainHandler.postDelayed(r, delayMillis)
    }

    fun registerReceiver(receiver: BroadcastReceiver?, @NonNull vararg actions: String) {
        if (receiver == null || actions?.size == 0)
            return

        val filter = IntentFilter()
        for (action in actions) {
            filter.addAction(action)
        }
        localBroadcastManager?.registerReceiver(receiver, filter)
    }

    fun unregisterReceiver(r: BroadcastReceiver?) {
        if (r == null)
            return
        localBroadcastManager?.unregisterReceiver(r)
    }


    fun sendBroadcast(intent: Intent): Boolean {
        return localBroadcastManager?.sendBroadcast(intent)!!
    }

    @JvmOverloads
    fun startActivityForCls(cls: Class<out Activity>, req: Int = defaultReqCode): Boolean {
        if (req == defaultReqCode) {
            context?.startActivity(Intent(context, cls))
        } else {
            if (context is Activity) {
                (context as Activity).startActivityForResult(Intent(context, cls), req)
                return true
            }
        }
        return false
    }

    @JvmOverloads
    fun startActivityForCls(cls: Class<out Activity>, options: Bundle, req: Int = defaultReqCode): Boolean {
        if (req == defaultReqCode) {
            val intent = Intent(context, cls)
            intent.putExtras(options)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            context?.startActivity(intent)
            return true
        } else {
            if (context is Activity) {
                val intent = Intent(context, cls)
                intent.putExtras(options)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                (context as Activity).startActivityForResult(intent, req)
                return true
            }
        }
        return false
    }

    @JvmOverloads
    fun startActivityForCls(cls: Class<out Activity>, value: String, req: Int = defaultReqCode): Boolean {
        val bundle = Bundle()
        bundle.putString(getTag(), value)
        return startActivityForCls(cls, bundle, req)
    }

    @JvmOverloads
    fun startActivityForCls(cls: Class<out Activity>, values: Array<String>, req: Int = defaultReqCode): Boolean {
        val bundle = Bundle()
        bundle.putStringArray(getTag(), values)
        return startActivityForCls(cls, bundle, req)
    }

    @JvmOverloads
    fun startActivityForCls(cls: Class<out Activity>, serializable: Serializable, req: Int = defaultReqCode): Boolean {
        val bundle = Bundle()
        bundle.putSerializable(getTag(), serializable)
        return startActivityForCls(cls, bundle, req)
    }

    @JvmOverloads
    fun startActivityForCls(cls: Class<out Activity>, parcelable: Parcelable, req: Int = defaultReqCode): Boolean {
        val bundle = Bundle()
        bundle.putParcelable(getTag(), parcelable)
        return startActivityForCls(cls, bundle, req)
    }

    @JvmOverloads
    fun startActivityForCls(cls: Class<out Activity>, parcelables: Array<Parcelable>, req: Int = defaultReqCode): Boolean {
        val bundle = Bundle()
        bundle.putParcelableArray(getTag(), parcelables)
        return startActivityForCls(cls, bundle, req)
    }


    fun release() {
        handlePool.release(this)
    }

    fun getBundle(): Bundle? {
        if (context is Activity) {
            return (context as Activity).getIntent().getExtras()
        }
        return null
    }

    fun setResult(bundle: Bundle) {
        if (context is Activity) {
            val intent = Intent()
            intent.putExtras(bundle)
            (context as Activity).setResult(Activity.RESULT_OK, intent)
        }
    }

}