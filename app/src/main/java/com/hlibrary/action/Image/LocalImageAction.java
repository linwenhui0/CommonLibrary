package com.hlibrary.action.Image;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore.Images.Media;
import android.support.annotation.NonNull;

import com.hlibrary.action.ImageAction;
import com.hlibrary.action.entity.BasePhoneInfo;
import com.hlibrary.action.listener.PhotosListener;
import com.hlibrary.util.BitmapDecoder;

import java.util.List;


/**
 * 
 * @author 林文辉
 * @since 2015-01-27
 * @version v 1.0.0
 * 
 */
public class LocalImageAction extends ImageAction implements PhotosListener {

	private PhotosListener photosListener;

	public LocalImageAction(@NonNull Activity activity) {
		super(activity);
	}

	/** @return 获取本地图片Intent */
	protected Intent localImgIntent() {
		Intent intent = new Intent(Intent.ACTION_PICK,
				Media.EXTERNAL_CONTENT_URI);
		intent.setDataAndType(Media.EXTERNAL_CONTENT_URI,"image/*");
		return intent;
	}

	/**
	 * @param data
	 * @return 根据Intent，取本地图片
	 */
	protected Bitmap getLocalSource(Intent data) {
		String imgPath = getImgPath(data);
		Bitmap bm = BitmapDecoder.decodeSampledBitmapFromFile(imgPath,
				getOutWidth(), getOutHeight());
		return bm;
	}

	/**
	 * @return 本地图片选择不带裁剪功能
	 */
	@Override
	public void startAction() {
		Intent intent = localImgIntent();
		activity.startActivityForResult(intent, PHOTO_LOCAL);
	}

	/**
	 * @return 图片选择的回调
	 */
	@Override
	public void startCropAction() {
		// 获取本地图片
		Intent intent = localImgIntent();
		// 获取本地图片进行剪切
		activity.startActivityForResult(intent, PHOTO_LOCAL_CROP_REQUEST);
	}

	/**
	 * @param num
	 *            当前已选几张
	 * @param cls
	 *            选择多图的Actvity
	 */
	public void startMulAction(int num, Class<?> cls) {
		Intent intent = new Intent(activity, cls);
		intent.putExtra("count", num);
		activity.startActivityForResult(intent, PHOTO_LOCALS);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
			throws Exception {
		if (Activity.RESULT_OK == resultCode) {
			Bitmap bm;
			switch (requestCode) {
			case PHOTO_LOCAL:
				bm = getLocalSource(data);
				if (!startRotate(bm))
					onPhoto(PHOTO_LOCAL, bm);
				break;
			case PHOTO_LOCAL_CROP_REQUEST:
				cropLocalPhoto(data);
				break;
			case PHOTO_LOCALS: {
				List<BasePhoneInfo> infos = (List<BasePhoneInfo>) data
						.getSerializableExtra(BasePhoneInfo.PHOTO_INFOS);
				onPhotos(infos);
			}
				break;
			}
		} else
			super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPhotos(final List<? extends BasePhoneInfo> photos) {
		if (photosListener == null)
			return;
		photosListener.onPhotos(photos);
	}

	public void setOnPhotosListener(PhotosListener photosListener) {
		this.photosListener = photosListener;
	}

}
