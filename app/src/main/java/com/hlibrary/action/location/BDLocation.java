package com.hlibrary.action.location;

import android.text.TextUtils;

import com.baidu.location.BDAbstractLocationListener;
import com.hlibrary.action.entity.LocationVo;
import com.hlibrary.action.listener.LocationListener;
import com.hlibrary.util.Logger;

public final class BDLocation extends BDAbstractLocationListener {

    private final static String TAG = "BDLocation";
    protected LocationListener locationListener;
    private LocationAction locationAction;

    public BDLocation(LocationListener locationListener, LocationAction locationAction) {
        this.locationListener = locationListener;
        this.locationAction = locationAction;
    }

    public BDLocation setLocationListener(LocationListener locationListener) {
        this.locationListener = locationListener;
        return this;
    }

    @Override
    public void onReceiveLocation(com.baidu.location.BDLocation bdLocation) {
        Logger.getInstance().i(TAG, bdLocation.getLocType() + " === " + bdLocation.getTime());
        final int type = bdLocation.getLocType();
        final boolean fitResult = type == com.baidu.location.BDLocation.TypeNetWorkLocation || type == com.baidu.location.BDLocation.TypeGpsLocation || type == com.baidu.location.BDLocation.TypeOffLineLocation;
        if (fitResult) {
            if (!TextUtils.isEmpty(bdLocation.getAddrStr())) {
                if (locationAction.isLocationOnce())
                    locationAction.locationStop();
                LocationVo locationVo = new LocationVo();
                locationVo.setAddr(bdLocation.getAddrStr());
                locationVo.setLat(bdLocation.getLatitude());
                locationVo.setLng(bdLocation.getLongitude());
                locationVo.setCity(bdLocation.getCity());
                if (locationListener != null)
                    locationListener.onLocation(locationVo);
            }
        }
    }
}
