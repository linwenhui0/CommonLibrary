package com.hlibrary.action;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore.MediaColumns;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

import com.hlibrary.action.listener.PhotoListener;

import java.io.File;


/**
 * 
 * @author 林文辉
 * @since 2015-01-27
 * @version v 1.0.0
 * 
 */
public abstract class ImageAction implements PhotoListener {

	/** 本地图片选择不带裁剪功能 */
	public static final int PHOTO_LOCAL = 0x1001;
	/** 图片选择的回调 */
	public static final int PHOTO_LOCAL_CROP_REQUEST = 0x1021;
	/** 本地图片选择带裁剪功能 */
	public static final int PHOTO_LOCAL_CROP = 0x1022;
	/** 拍照选照片不带裁剪功能 */
	public static final int PHOTO_CAMERA = 0x1003;
	/** 拍照选照片带裁剪功能 */
	public static final int PHOTO_CAMERA_CROP = 0x1004;
	/** 选择多图 */
	public static final int PHOTO_LOCALS = 0x1005;
	/** 图片旋转 */
	public static final int PHOTO_ROTALE = 0x1006;
	public static final String PHOTO_ROTATE_EXTRA = "pic";

	protected Activity activity;
	private Class<?> rotateCls;

	private int outputX;
	private int outputY;

	private int outWidth = 240;
	private int outHeight = 320;

	private PhotoListener listener;

	public ImageAction(@NonNull Activity activity) {
		this.activity = activity;
		DisplayMetrics metrics = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		final int w = metrics.widthPixels / 4;
		final int h = metrics.heightPixels / 4;
		outputX = w > h ? h : w;
		outputY = outputX;
	}

	/**
	 * @param rotateCls
	 *            指定旋转图片的Activity
	 */
	public void setRotateCls(Class<?> rotateCls) {
		this.rotateCls = rotateCls;
	}

	public abstract void startAction();

	public abstract void startCropAction();

	/**
	 * @param requestCode
	 *            返回请求码
	 * @param resultCode
	 *            执行的结果码
	 * @param data
	 *            返回的数据
	 * @throws Exception
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent data)
			throws Exception {
		if (Activity.RESULT_OK == resultCode) {
			switch (requestCode) {
			case PHOTO_LOCAL_CROP: {
				Bitmap bm = data.getParcelableExtra("data");
				onPhoto(PHOTO_LOCAL_CROP, bm);
			}
				break;

			case PHOTO_ROTALE: {
				Bitmap bm = data.getParcelableExtra(PHOTO_ROTATE_EXTRA);
				onPhoto(PHOTO_ROTALE, bm);
			}
				break;
			}

		}
	}

	/** @return 获取系统剪切图片的Intent */
	protected Intent getSystemCropIntent(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", getOutputX());
		intent.putExtra("outputY", getOutputY());
		intent.putExtra("return-data", true);
		return intent;
	}

	/**
	 * 
	 * @param data
	 *            返回数据
	 * @return 获取图片的所在的绝对路径
	 */
	protected String getImgPath(Intent data) {
		Uri picSource = data.getData();
		// 列表
		String[] picList = { MediaColumns.DATA };
		Cursor imgData = activity.getContentResolver().query(picSource,
				picList, null, null, null);
		imgData.moveToFirst();
		String imgPath = imgData.getString(0);
		imgData.close();
		return imgPath;
	}

	/**
	 * @return 对图片进行裁切
	 */
	protected void cropLocalPhoto(Intent data) {
		String imgPath = getImgPath(data);
		Uri uri = Uri.fromFile(new File(imgPath));
		Intent intent = getSystemCropIntent(uri);
		activity.startActivityForResult(intent, PHOTO_LOCAL_CROP);
	}

	@Override
	public void onPhoto(final int bimType, final Bitmap bm) {
		if (listener == null)
			return;
		listener.onPhoto(bimType, bm);
	}

	/**
	 * @return 图片旋转<br/>
	 *         ture：对图片旋转成功
	 */
	protected boolean startRotate(Bitmap bm) {
		if (rotateCls == null)
			return false;
		Intent intent = new Intent(this.activity, rotateCls);
		intent.putExtra(PHOTO_ROTATE_EXTRA, bm);
		this.activity.startActivityForResult(intent, PHOTO_ROTALE);
		return true;
	}

	public void setOnBitmapListener(PhotoListener listener) {
		this.listener = listener;
	}

	public int getOutWidth() {
		return outWidth;
	}

	public void setOutWidthAndHeight(int outWidthHeight) {
		this.outWidth = outWidthHeight;
		this.outHeight = outWidthHeight;
	}

	public int getOutHeight() {
		return outHeight;
	}

	public int getOutputX() {
		return outputX;
	}

	public void setOutputX(int outputX) {
		this.outputX = outputX;
	}

	public int getOutputY() {
		return outputY;
	}

	public void setOutputY(int outputY) {
		this.outputY = outputY;
	}

}
