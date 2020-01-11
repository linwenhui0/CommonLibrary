package com.hlibrary.base

import android.widget.BaseAdapter
import android.widget.Filter
import java.util.*

/**
 *
 * @author 林文辉
 * @param <T>
 * 过滤的实体
 * @version v1.0.0
 * @since 2015-01-27
</T> */
abstract class BaseFilter(protected var mData: List<*>, private val mAdapter: BaseAdapter) : Filter() {
    private var mFilterData: ArrayList<Any>? = null
    override fun performFiltering(prefix: CharSequence): FilterResults {
        val results = FilterResults()
        if (mFilterData == null) {
            mFilterData = ArrayList<Any>(mData)
        }
        if (prefix.isNotEmpty()) {
            val unfilteredValues: ArrayList<Any> = mFilterData!!
            val count = unfilteredValues.size
            val newValues = ArrayList<Any>(count)
            for (i in 0 until count) {
                val h = unfilteredValues[i]
                val isInclude = isInclude(h, prefix)
                if (isInclude) {
                    newValues.add(h)
                    continue
                }
            }
            results.values = newValues
            results.count = newValues.size
        } else {
            val list: ArrayList<Any> = mFilterData!!
            results.values = list
            results.count = list.size
        }
        return results
    }

    /**
     * @param model
     * @param prefix
     * 过虑的关键字
     * @return 过滤条件
     */
    abstract fun isInclude(model: Any, prefix: CharSequence?): Boolean

    override fun publishResults(constraint: CharSequence, results: FilterResults) {
        if (results.values is List<*> ) {
            try {
                mData = results.values as List<*>
                if (results.count > 0) {
                    mAdapter.notifyDataSetChanged()
                } else {
                    mAdapter.notifyDataSetInvalidated()
                }
            } catch (e: Exception) {
            }
        } else {
            mAdapter.notifyDataSetInvalidated()
        }
    }

}