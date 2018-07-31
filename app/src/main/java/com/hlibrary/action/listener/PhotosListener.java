package com.hlibrary.action.listener;


import com.hlibrary.action.entity.BasePhoneInfo;

import java.util.List;

/**
 * 
 * @author 林文辉
 * @version v 1.0.0
 * @since 2015-01-27
 * 
 */
public interface PhotosListener {
	  void onPhotos(List<? extends BasePhoneInfo> photos);
}
