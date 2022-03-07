package com.hlibrary.adapter.recycler

import android.view.View

/**
 * @author linwh
 * @since 2015-08-26
 */
interface OnItemClickListener<T> {
    fun onItemClick(v: View?, position: Int, bean: T?)
}