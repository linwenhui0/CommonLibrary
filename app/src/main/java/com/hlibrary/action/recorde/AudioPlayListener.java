package com.hlibrary.action.recorde;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

/**
 *
 *      按钮对应的 TAG 为 音频 的URL地址<br />
 * @since 2013-4-8
 * 
 */
public class AudioPlayListener implements OnClickListener {
	private String url; // 音频网络地址.
	private AudioPlay mAudioPlay;
	private Context context;
	private AudioPlayStatus playStatus;

	public AudioPlayListener(Context context) {
		super();
		this.context = context;
		this.playStatus = new AudioPlayStatus();
		this.mAudioPlay = new AudioPlay(this.context) {
			@Override
			protected void onPlayStart() {
				super.onPlayStart();
				playStatus.playing();
			}

			@Override
			protected void onPlayStop() {
				super.onPlayStop();
				playStatus.pause();
			}

		};
	}

	@Override
	public void onClick(View v) {
		// MessageInfo msg = (MessageInfo) v.getTag();
		// if (msg == null)
		// return;
		//
		// if (4 == msg.getSendState()) {
		// down(msg);
		// return;
		// }
		//
		// url = msg.getContent();
		// // 为button添加播放状态
		// this.playStatus.setBtn((ImageView) v);
		// this.mAudioPlay.play(url);
	}

	public void stop() {
		mAudioPlay.stop();
	}

	class AudioPlayStatus {
		private AnimationDrawable animBtnPlay;

		private AnimationDrawable tempAnimBtnPlay;

		private ImageView btn;

		public AudioPlayStatus() {
			super();
		}

		public void setBtn(ImageView btn) {
			this.btn = btn;
			initParam();
		}

		private void initParam() {
			if (animBtnPlay != null) {
				tempAnimBtnPlay = animBtnPlay;
			}
			animBtnPlay = (AnimationDrawable) this.btn.getDrawable();
			pause();
		}

		/**
		 * 等待位置..
		 * 
		 * 作者:fighter <br />
		 * 创建时间:2013-4-19<br />
		 * 修改时间:<br />
		 */
		public void pause() {
			if (btn != null && animBtnPlay != null) {
				animBtnPlay.stop();
				animBtnPlay.selectDrawable(0);
			}
		}

		/**
		 * 播放音频状态.
		 * 
		 * 作者:fighter <br />
		 * 创建时间:2013-4-19<br />
		 * 修改时间:<br />
		 */
		public void playing() {
			if (btn != null && animBtnPlay != null) {
				if (tempAnimBtnPlay != null && tempAnimBtnPlay.isRunning()) {
					tempAnimBtnPlay.stop();
					tempAnimBtnPlay.selectDrawable(0);
				}
				animBtnPlay.start();
			}

		}

	}

}
