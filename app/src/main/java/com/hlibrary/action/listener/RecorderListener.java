package com.hlibrary.action.listener;

public interface RecorderListener {
    /**
     * @param amplitude 说话的声倍 <br />
     * @since:2013-1-22<br />
     */
    void recording(double amplitude);

    /**
     * @param path 录制的声音的路径 null 表示没有录制成功,说话时间少于 1 秒 <br />
     * @since 2013-1-23<br />
     */
    void stop(String path);

    void onStart();

    /**
     * @param recordTime 录音时长
     * @return 录制的时间
     * @since 2013-1-30<br />
     */
    void recordTime(float recordTime);
}
