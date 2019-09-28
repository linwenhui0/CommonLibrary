package com.hlibrary.action.recorde;

import java.io.IOException;

public abstract class BaseAudioRecorder {

	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public abstract void start() throws IOException;

	public abstract void stop() throws IOException;

	public abstract double getAmplitude();
}