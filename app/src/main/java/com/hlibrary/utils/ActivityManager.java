package com.hlibrary.utils;


import com.hlibrary.base.activity.BaseActivity;

import java.util.TreeMap;


/**
 * @author linwh
 * @date 2015-09-06
 */
public class ActivityManager {

    private TreeMap<String, BaseActivity> activities;


    public ActivityManager() {
        activities = new TreeMap();
    }

    public void pushActivity(BaseActivity baseActivity) {
        activities.put(baseActivity.getClass().getSimpleName(), baseActivity);
    }

    public void pullActivity(BaseActivity baseActivity) {
        activities.remove(baseActivity.getClass().getSimpleName());
    }

    public BaseActivity getCurActivity() {
        return activities.lastEntry().getValue();
    }

    public String getCurActivityName() {
        if (activities.isEmpty())
            return "";
        return activities.lastKey();
    }

    public void exit() {
        for (String key : activities.keySet()) {
            BaseActivity baseActivity = activities.remove(key);
            baseActivity.finish();
        }
    }

}
