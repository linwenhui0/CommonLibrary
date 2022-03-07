package com.hlibrary.adapter.model

/**
 *
 * @author linwenhui
 * @date 2017/2/28
 */
open class HeadFootAdapterVo<T>(override val layoutResId: Int) : AdapterVo(layoutResId) {
    var data: T? = null
}