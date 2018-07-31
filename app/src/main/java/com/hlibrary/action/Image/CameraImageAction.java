package com.hlibrary.action.Image;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import com.hlibrary.action.ImageAction;
import com.hlibrary.util.BitmapDecoder;
import com.hlibrary.util.SDUtil;
import com.hlibrary.util.ToastUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author 林文辉
 * @since 2015-01-27
 * @version v 1.0.0
 * @说明 相机图片选择
 * 
 */
public class CameraImageAction extends ImageAction {

	private File cameraDir, currCameraPhotoFile;

	public CameraImageAction(@NonNull Activity activity) {
		super(activity);
		cameraDir = new File(
				android.os.Environment.getExternalStorageDirectory(),
				"/Android/data/" + activity.getPackageName() + "/Camera");
		if (SDUtil.ExistSDCard() && !cameraDir.exists()) {
			if (!cameraDir.mkdirs()) {
				ToastUtil.showShortTime(activity, "SD卡拔出，不能使用某些功能!");
			}
		}
	}

	/**
	 * @author 林文辉
	 * @since 2013-01-29
	 * @return 获取相机图片Intent 并将图片存放在 mnt/sdcard/DCIM/Camera/ 下<br />
	 * 
	 */
	protected Intent getCameraIntent() {
		currCameraPhotoFile = new File(cameraDir, getPhotoFileName());
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,
				Uri.fromFile(currCameraPhotoFile));
		return intent;
	}

	/**
	 * @return 拍照选照片不带裁剪功能
	 */
	@Override
	public void startAction() {
		Intent intent = getCameraIntent();
		activity.startActivityForResult(intent, PHOTO_CAMERA);
	}

	/**
	 * @return 拍照选照片不带裁剪功能
	 */
	@Override
	public void startCropAction() {
		Intent intent = getCameraIntent();
		activity.startActivityForResult(intent, PHOTO_CAMERA_CROP);
	}

	/**
	 * @return 调相机生成的图片
	 */
	private Bitmap getCameraSource() {
		Bitmap bm = BitmapDecoder.decodeSampledBitmapFromFile(
				currCameraPhotoFile.getPath(), getOutWidth(), getOutHeight());
		return bm;
	}

	/**
	 * @return 照片裁剪功能
	 */
	private void cropCameraPhoto() {
		Uri data = Uri.fromFile(currCameraPhotoFile);
		Intent intent = getSystemCropIntent(data);
		activity.startActivityForResult(intent, PHOTO_LOCAL_CROP);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
			throws Exception {
		if (Activity.RESULT_OK == resultCode) {
			Bitmap bm = null;
			switch (requestCode) {
			case PHOTO_CAMERA:
				bm = getCameraSource();
				if (!startRotate(bm))
					onPhoto(PHOTO_CAMERA, bm);
				break;
			case PHOTO_CAMERA_CROP:
				if (!startRotate(bm))
					cropCameraPhoto();
			}
		} else
			super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * @return 生成图片名
	 */
	public static String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";

	}

}
