package com.hlibrary.widget.listener

/**
 * Created by linwenhui on 2017/8/30.
 */
interface IReceiverCodeListener {
    fun onEmptyPhoneNumber()
    fun onPhoneNumberValid()
    fun onTick(tick: Long)
    fun onFinish()
}