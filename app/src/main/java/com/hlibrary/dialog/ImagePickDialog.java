package com.hlibrary.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.hlibrary.ImagePick;
import com.hlibrary.R;
import com.hlibrary.action.Image.CameraImageAction;
import com.hlibrary.action.Image.LocalImageAction;
import com.hlibrary.action.listener.PhotoListener;
import com.hlibrary.base.dialog.BaseDialog;
import com.hlibrary.util.Logger;

/**
 * Created by linwenhui on 2015/11/24.
 */
public class ImagePickDialog extends BaseDialog<ImagePick> implements PhotoListener {
    private final static String TAG = "ImagePickDialog";
    private CameraImageAction cameraImageAction;
    private LocalImageAction localImageAction;
    private int sel = -1;
    private PhotoListener onBitmapListener;
    private Activity activity;

    public ImagePickDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_image_pick);
        mDataBinding.setCameraOnClickListener(new CameraOnClickImp());
        mDataBinding.setPhoneOnClickListener(new PhotoOnCLickImp());
        mDataBinding.setDismissOnClickListener(new DismissOnclickImp());
        cameraImageAction = new CameraImageAction(activity);
        cameraImageAction.setOnBitmapListener(this);
        localImageAction = new LocalImageAction(activity);
        localImageAction.setOnBitmapListener(this);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = Gravity.BOTTOM;
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setAttributes(attributes);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.getInstance().i(TAG, " == onActivityResult == sel = " + sel);
        if (sel == 0) {
            try {
                cameraImageAction.onActivityResult(requestCode, resultCode, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (sel == 1) {
            try {
                localImageAction.onActivityResult(requestCode, resultCode, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sel = -1;
    }

    public void setOnBitmapListener(PhotoListener onBitmapListener) {
        this.onBitmapListener = onBitmapListener;
    }

    @Override
    public void onPhoto(int bimType, Bitmap bm) {
        if (onBitmapListener != null)
            onBitmapListener.onPhoto(bimType, bm);
    }

    private class CameraOnClickImp implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            sel = 0;
            dismiss();
            cameraImageAction.startAction();
        }
    }

    private class PhotoOnCLickImp implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            sel = 1;
            dismiss();
            localImageAction.startAction();
        }
    }

    private class DismissOnclickImp implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            dismiss();
        }
    }
}
