package com.hlibrary.action.entity;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * @author 林文辉
 * @version v 1.0.0
 * @since 2015-01-27
 */
public class BasePhoneInfo implements Serializable {

    public static final String PHOTO_INFOS = "PHOTO_INFOS";

    /**
     *
     */
    private static final long serialVersionUID = -7184597475809248127L;

    private Bitmap bitmap;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }


}
