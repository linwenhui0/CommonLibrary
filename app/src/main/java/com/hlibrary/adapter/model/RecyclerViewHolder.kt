package com.hlibrary.adapter.model

import android.util.SparseArray
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * @author linwh
 * @date 2016-09-06
 */
class RecyclerViewHolder<V:ViewDataBinding>(private val mConvertView: View) : ViewHolder(mConvertView) {
    private val mViews: SparseArray<View?> = SparseArray()
    var binding: V? = null

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    fun <T : View?> getView(viewId: Int): T? {
        var view = mViews[viewId]
        if (view == null) {
            view = mConvertView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T?
    }

}