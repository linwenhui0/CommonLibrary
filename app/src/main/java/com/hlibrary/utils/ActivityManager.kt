package com.hlibrary.utils

import com.hlibrary.base.activity.BaseActivity
import java.util.*

/**
 * @author linwh
 * @date 2015-09-06
 */
class ActivityManager private constructor() {
    private val activities: TreeMap<String?, BaseActivity<*>?> = TreeMap()

    fun pushActivity(baseActivity: BaseActivity<*>) {
        activities[baseActivity.javaClass.simpleName] = baseActivity
    }

    fun popActivity(baseActivity: BaseActivity<*>) {
        activities.remove(baseActivity.javaClass.simpleName)
    }

    val curActivity: BaseActivity<*>?
        get() = activities.lastEntry().value

    val curActivityName: String?
        get() = if (activities.isEmpty()) {
            ""
        } else activities.lastKey()

    fun exit() {
        for (key in activities.keys) {
            val baseActivity = activities.remove(key)
            baseActivity!!.finish()
        }
    }

    companion object {
        val instance by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED){
            ActivityManager()
        }
    }


}