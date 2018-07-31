package com.hlibrary.action.recorde;

import android.media.AudioFormat;
import android.media.MediaRecorder;

public abstract class BaseRecord {

	public final static int DEFAULT_ANDIO_SOURCE = MediaRecorder.AudioSource.MIC;
	public final static int DEFAULT_SAMPLE_RATE = 44100;
	public final static int DEFAULT_CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_STEREO;
	public final static int DEFAULT_AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT;

	protected AmplitudeListener recordListener;
	private boolean isRecord = false;
	// NewAudioName可播放的音频文件
	protected String outputAudio;

	/**
	 * @param outputAudio 录音输出文件
	 */
	public void setOutputAudio(String outputAudio) {
		this.outputAudio = outputAudio;
	}

	/**
	 * 开始录音
	 */
	public void startRecord() {
		isRecord = true;
	}

	/**
	 * 停止录音
	 */
	public void stopRecord() {
		isRecord = false;
	}

	public boolean isRecord() {
		return isRecord;
	}

	public void setAmplitudeListener(AmplitudeListener recordListener) {
		this.recordListener = recordListener;
	}

	/**
	 * 录音分贝数
	 */
	public interface AmplitudeListener {
		void onAmplitude(double amplitude);
	}

}
