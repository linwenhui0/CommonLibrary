package com.hlibrary.entity;

/**
 * Created by linwenhui on 2017/3/31.
 */

public class MessageEvent {

    protected int type = -1;
    protected String key = null;

    public MessageEvent(int type) {
        this.type = type;
    }

    public MessageEvent(String key) {
        this.key = key;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
