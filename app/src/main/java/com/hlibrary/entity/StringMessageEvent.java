package com.hlibrary.entity;

/**
 * Created by linwenhui on 2017/4/22.
 */

public class StringMessageEvent extends MessageEvent {
    private String targeObj;

    public StringMessageEvent(int type, String targeObj) {
        super(type);
        this.targeObj = targeObj;
    }

    public StringMessageEvent(String key, String targeObj) {
        super(key);
        this.targeObj = targeObj;
    }

    public String getTargeObj() {
        return targeObj;
    }

    public void setTargeObj(String targeObj) {
        this.targeObj = targeObj;
    }
}
