package com.hlibrary.entity

import android.graphics.Bitmap
import java.io.Serializable

/**
 * @author 林文辉
 * @version v 1.0.0
 * @since 2015-01-27
 */
class BasePhoneInfo : Serializable {
    var bitmap: Bitmap? = null

    companion object {
        private const val serialVersionUID = -7184597475809248127L
    }
}