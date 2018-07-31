package com.hlibrary.action;

import android.content.Context;

import com.hlibrary.action.listener.RecorderListener;

import java.io.File;
import java.io.IOException;

/**
 * 
 * @author 林文辉
 * @since 2015-01-27
 * @version v 1.0.0
 * 
 */
public abstract class RecorderAction {

	/** 准备 */
	public static final int RECOREDER_ON = 0;
	/** 正在进行 */
	public static final int RECOREDER_ING = 1;
	/** 结束 */
	public static final int RECOREDER_END = 2;

	protected Context context;
	protected RecorderListener recorderListener;

	public RecorderAction(Context context) {
		this.context = context;
	}

	/**
	 * @return 开始录制
	 * 
	 * @param recordFile
	 *            音频录制存放的位置.
	 * @throws IOException
	 *             SD卡不能使用，不能建立文件时抛出异常! 作者:fighter <br />
	 *             创建时间:2013-5-7<br />
	 *             修改时间:<br />
	 */
	public abstract void startRecord(String recordFile) throws IOException;

	/**
	 * @return 开始录制
	 * 
	 * @param recordFile
	 *            音频录制存放的位置.
	 * @throws IOException
	 *             SD卡不能使用，不能建立文件时抛出异常! 作者:fighter <br />
	 *             创建时间:2013-5-7<br />
	 *             修改时间:<br />
	 */
	public abstract void startRecord(File recordFile) throws IOException;

	/**
	 * @return 停止录音
	 * 
	 * @throws IOException
	 *             作者:fighter <br />
	 *             创建时间:2013-1-30<br />
	 *             修改时间:<br />
	 */
	public abstract void stopRecord() throws IOException;

	/**
	 * @return 删除录音文件
	 */
	public abstract void delRecord();

	public void setRecorderListener(RecorderListener recorderListener) {
		this.recorderListener = recorderListener;
	}

}
