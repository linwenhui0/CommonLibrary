/**
 * @Title: LocationAction.java
 * @Package com.shangwupanlv.app.action
 * @Description: TODO(用一句话描述该文件做什么)
 * @author Alex.Z
 * @date 2013-6-1 上午10:24:23
 * @version V1.0
 */
package com.hlibrary.action.location;

import android.content.Context;

import com.hlibrary.util.Logger;


/**
 * @author 林文辉
 * @version v 1.0.0
 * @since 2015-01-27
 * 百度地图离线定位
 */
public class LocationAction extends BaseLocationAction {
    final static String CoorType_GCJ02 = "gcj02";//火星坐标,中国坐标偏移标准，Google Map、高德、腾讯使用
    final static String CoorType_BD09LL = "bd09ll";//百度坐标偏移标准
    final static String CoorType_BD09MC = "bd09";
    /***
     * 61 ： GPS定位结果，GPS定位成功。
     * 62 ： 无法获取有效定位依据，定位失败，请检查运营商网络或者wifi网络是否正常开启，尝试重新请求定位。
     * 63 ： 网络异常，没有成功向服务器发起请求，请确认当前测试手机网络是否通畅，尝试重新请求定位。
     * 65 ： 定位缓存的结果。
     * 66 ： 离线定位结果。通过requestOfflineLocaiton调用时对应的返回结果。
     * 67 ： 离线定位失败。通过requestOfflineLocaiton调用时对应的返回结果。
     * 68 ： 网络连接失败时，查找本地离线定位时对应的返回结果。
     * 161： 网络定位结果，网络定位定位成功。
     * 162： 请求串密文解析失败。
     * 167： 服务端定位失败，请您检查是否禁用获取位置信息权限，尝试重新请求定位。
     * 502： key参数错误，请按照说明文档重新申请KEY。
     * 505： key不存在或者非法，请按照说明文档重新申请KEY。
     * 601： key服务被开发者自己禁用，请按照说明文档重新申请KEY。
     * 602： key mcode不匹配，您的ak配置过程中安全码设置有问题，请确保：sha1正确，“;”分号是英文状态；且包名是您当前运行应用的包名，请按照说明文档重新申请KEY。
     * 501～700：key验证失败，请按照说明文档重新申请KEY。
     */
    private static final String TAG = "LocationAction";
//    private LocationClient locationClient = null;
    private int distanceTime = 3000;
    private BDLocation bdLocation;

    public LocationAction(Context context) {
        super(context);
//        locationClient = new LocationClient(context);
        updateOption();
        bdLocation = new BDLocation(locationListener, this);
//        locationClient.registerLocationListener(bdLocation);
    }

    protected int getScanSpan() {
        if (isLocationOnce()) {
            return 0;
        } else {
            return distanceTime;
        }
    }

    public LocationAction setDistanceTime(int distanceTime) {
        this.distanceTime = distanceTime;
        return this;
    }

    public void updateOption() {
//        LocationClientOption mOption = locationClient.getLocOption();
//        if (mOption == null) {
//            mOption = new LocationClientOption();
//            mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//            mOption.setCoorType(CoorType_GCJ02);//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
//            mOption.setScanSpan(getScanSpan());//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//            mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
//            mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
//            mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
//            mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
//            mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//            mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//            mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//            mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
//        } else {
//            mOption.setScanSpan(getScanSpan());//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        }
//        locationClient.setLocOption(mOption);
    }

    @Override
    public synchronized void locationStart() {
        Logger.getInstance().d(TAG, "locationStart()");
//        if (locationClient != null && !locationClient.isStarted()) {
//            locationClient.start();
//        }
    }

    @Override
    public synchronized void locationStop() {
        Logger.getInstance().d(TAG, "locationStop()");
//        if (locationClient != null && locationClient.isStarted()) {
//            locationClient.stop();
//        }
    }

    @Override
    public void onDestory() {
//        locationClient.unRegisterLocationListener(bdLocation);
    }


}
