package com.hlibrary.adapter.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.hlibrary.adapter.model.AdapterVo

/**
 * @author linwenhui
 * @date 2015/9/14.
 */
abstract class MulCommonDataAdapter<T : AdapterVo?>(context: Context, variableId: Int) : ObjectBaseAdapter<T>(context, 0, variableId) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        var itemView = convertView
        val vo = getItem(position)
        var adapterBinding: ViewDataBinding
        if (itemView == null) {
            adapterBinding = DataBindingUtil.inflate(inflater, vo!!.layoutResId, parent, false)
            itemView = adapterBinding.root
            itemView.tag = adapterBinding
            itemView.setTag(99, vo)
        } else {
            adapterBinding = itemView.tag as ViewDataBinding
            val tagPos = itemView.getTag(99)
            try {
                val preVo: T? = tagPos as T?
                if (preVo?.layoutResId != vo?.layoutResId) {
                    adapterBinding = DataBindingUtil.inflate(inflater, vo!!.layoutResId, parent, false)
                    itemView = adapterBinding.root
                    itemView.tag = adapterBinding
                    itemView.setTag(99, vo)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        adapterBinding.setVariable(variableId, getItem(position))
        adapterBinding.executePendingBindings()
        return itemView
    }
}