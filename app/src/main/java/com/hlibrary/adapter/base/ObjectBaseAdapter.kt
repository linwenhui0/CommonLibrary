package com.hlibrary.adapter.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import java.util.*

/**
 * @param <T>
 * @updatedate 2017-8-16
</T> */
open class ObjectBaseAdapter<T>(context: Context, protected val mItemLayoutId: Int, protected val variableId: Int) : BaseAdapter() {
    @JvmField
    protected var data: MutableList<T> = ArrayList()
    protected var inflater: LayoutInflater = LayoutInflater.from(context.applicationContext)

    fun addObject(t: T?) {
        if (t != null) {
            data.add(t)
        }
    }

    fun addObject(pos: Int, t: T?) {
        if (pos > -1 && pos < count && t != null) {
            data.add(pos, t)
        }
    }

    fun removeObject(t: T) {
        data.remove(t)
    }

    fun removeObject(pos: Int) {
        if (pos > -1 && pos < count) {
            data.removeAt(pos)
        }
    }

    fun addAll(ts: Collection<T>?) {
        if (ts != null && !ts.isEmpty()) {
            data.addAll(ts)
        }
    }

    fun addAll(pos: Int, ts: Collection<T>) {
        if (pos > -1 && pos < count && ts != null) {
            data.addAll(pos, ts)
        }
    }

    fun removeAll() {
        data.clear()
    }

    fun indexOf(model: T?): Int {
        return if (model != null) {
            data.indexOf(model)
        } else -1
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): T? {
        return if (position < count) {
            data[position]
        } else {
            null
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var itemView = convertView
        var adapterBinding: ViewDataBinding? = null
        if (itemView == null) {
            adapterBinding = DataBindingUtil.inflate(inflater, mItemLayoutId, parent, false)
            if (adapterBinding != null) {
                itemView = adapterBinding.root
                itemView.tag = adapterBinding
            } else {
                itemView = inflater.inflate(mItemLayoutId, parent, false)
            }
        } else {
            val tag = itemView.tag
            if (tag is ViewDataBinding) {
                adapterBinding = tag as ViewDataBinding
            }
        }
        val vo = getItem(position)
        if (adapterBinding != null) {
            adapterBinding.setVariable(variableId, vo)
            adapterBinding.executePendingBindings()
        }
        return itemView
    }


}