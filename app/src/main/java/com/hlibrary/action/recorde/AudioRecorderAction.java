package com.hlibrary.action.recorde;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.hlibrary.action.RecorderAction;

import java.io.File;
import java.io.IOException;

/**
 * 
 * 功能：录音<br />
 * 通过 使用SDK中AudioRecorder调用硬件进行录音<br/>
 * 通过录音可以返回音频的播率{@link #getAmplitude()}<br/>
 * 通过添加监听{@link #recorderListener} 对录音过程进行时时监听,通过接口时时反应!<br/>
 * 日期：2013-1-22<br />
 * 地点：风搜科技<br />
 * 版本：ver 1.0<br />
 * 
 * @author fighter
 * @since
 */
public class AudioRecorderAction extends RecorderAction {
	private static final int MIN_TIME = 1; // 录音的下线

	public static int recordStart = RECOREDER_ON;

	private float recordTime = 0.0f; // 录制的时间
	private String recordPath;

	private BaseAudioRecorder audioRecorder;
	private static Handler mainHandler = new Handler(Looper.getMainLooper());

	public AudioRecorderAction(Context context) {
		this(context, new AudioRecorder());
	}

	public AudioRecorderAction(Context context, BaseAudioRecorder audioRecorder) {
		super(context);
		this.audioRecorder = audioRecorder;
	}

	/**
	 * 开始录制
	 * 
	 * @param recordFile
	 *            音频录制存放的位置.
	 * @throws IOException
	 *             SD卡不能使用，不能建立文件时抛出异常! 作者:fighter <br />
	 *             创建时间:2013-5-7<br />
	 *             修改时间:<br />
	 */
	@Override
	public void startRecord(String recordFile) throws IOException {
		startRecord(new File(recordFile));
	}

	/**
	 * 开始录制
	 * 
	 * @param recordFile
	 *            音频录制存放的位置.
	 * @throws IOException
	 *             SD卡不能使用，不能建立文件时抛出异常! 作者:fighter <br />
	 *             创建时间:2013-5-7<br />
	 *             修改时间:<br />
	 */
	@Override
	public void startRecord(File recordFile) throws IOException {
		mainHandler.post(new RecordStartRunnable());
		this.recordPath = recordFile.getPath();
		audioRecorder.setPath(recordPath);
		try {
			audioRecorder.start();
			recordStart = RECOREDER_ING;
			recordTime = 0.0f;
			mainHandler.post(new RecordingStatusRunnable());
		} catch (IOException e) {
			recordStart = RECOREDER_END;
			mainHandler.removeCallbacks(new RecordingStatusRunnable());
			throw e;
		}
	}

	/**
	 * 停止录音
	 * 
	 * @throws IOException
	 *             作者:fighter <br />
	 *             创建时间:2013-1-30<br />
	 *             修改时间:<br />
	 */
	@Override
	public void stopRecord() throws IOException {
		if (null == audioRecorder) {
			return;
		}

		recordStart = RECOREDER_END;
		mainHandler.removeCallbacks(new RecordingStatusRunnable());
		try {
			audioRecorder.stop();
			if (MIN_TIME > recordTime) {
				delRecord();
				recordPath = null;
			}
			Runnable r = new RecordEndRunnable();
			mainHandler.post(r);
		} catch (IOException e) {
			throw e;
		}

	}

	/*
	 * 删除录音文件
	 */
	@Override
	public void delRecord() {
		if (null != recordPath) {
			File file = new File(recordPath);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	public float getRecordTime() {
		return recordTime;
	}

	public double getAmplitude() {
		return audioRecorder.getAmplitude();
	}

	public String getRecordPath() {
		return recordPath;
	}

	class RecordStartRunnable implements Runnable {

		@Override
		public void run() {
			recorderListener.onStart();
		}
	}

	class RecordingRunnable implements Runnable {

		private double amplitude;

		public RecordingRunnable(double amplitude) {
			this.amplitude = amplitude;
		}

		@Override
		public void run() {
			recorderListener.recording(amplitude);
		}
	}

	class RecordingStatusRunnable implements Runnable {

		@Override
		public void run() {
			Runnable recordingRunnable = new RecordingRunnable(
					audioRecorder.getAmplitude());
			mainHandler.post(recordingRunnable);
			recordTime += 0.2f;
			mainHandler.postDelayed(new RecordingStatusRunnable(), 200);
		}
	}

	class RecordEndRunnable implements Runnable {
		@Override
		public void run() {
			recorderListener.stop(recordPath);
			recorderListener.recordTime(recordTime);
		}
	}
}
