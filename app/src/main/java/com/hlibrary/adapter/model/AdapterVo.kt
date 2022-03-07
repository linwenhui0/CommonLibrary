package com.hlibrary.adapter.model

import java.io.Serializable

/**
 *
 * @author linwenhui
 * @date 2016/11/2
 */
open class AdapterVo(open val layoutResId: Int) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (other is AdapterVo) {
            return layoutResId == other.layoutResId
        }
        return super.equals(other)
    }

}