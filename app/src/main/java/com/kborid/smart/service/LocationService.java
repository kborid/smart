package com.kborid.smart.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.SoundPool;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.kborid.library.common.UIHandler;
import com.kborid.library.util.LogUtils;
import com.kborid.smart.manager.LocationManager;

/**
 * 高德定位Service
 */
public class LocationService extends Service implements AMapLocationListener {
    private static final String TAG = "LocationService";
    private static final int DELAY_TIME = 8000;
    private AMapLocationClient mLocationClient = null;
    private AMapLocation location;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mLocationClient = new AMapLocationClient(this);
        mLocationClient.setLocationListener(this);
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//快频率定位模式,定位精度高
        mLocationOption.setOnceLocation(false);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setInterval(3000);//间隔3秒返回结果
        mLocationClient.setLocationOption(mLocationOption);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UIHandler.getHandler().removeCallbacksAndMessages(0);
        if (mLocationClient != null){
            mLocationClient.onDestroy();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mLocationClient.startLocation();
        //8秒后，检查GPS信号
        UIHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (location != null){
                    LogUtils.d(TAG, "检查GPS信号");
                    parseGPSStatusString(location.getLocationQualityReport().getGPSStatus());
                }
            }
        }, DELAY_TIME);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null){
            location = aMapLocation;
            LocationManager.lon = aMapLocation.getLongitude();
            LocationManager.lat = aMapLocation.getLatitude();
            LocationManager.cityCode = aMapLocation.getCityCode();
        }
    }

    public static void startLocationService(Context context) {
        Intent intent = new Intent(context, LocationService.class);
        context.startService(intent);
    }



    /**
     * 文字转语音
     * @param text
     */
    public void speck(String text){
//        VoiceCenterProcessor.Builder.mInstance.speakVoice(text);
    }


    /**
     * 解析GPS状态的
     * @param statusCode GPS状态码
     * @return
     */
    private void parseGPSStatusString(int statusCode) {
        String str = "";
        switch (statusCode) {
            case AMapLocationQualityReport.GPS_STATUS_OK:
                str = "GPS状态正常";
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
                str = "手机中没有GPS Provider，无法进行GPS定位";
                speck("GPS信号弱");
                break;
            case AMapLocationQualityReport.GPS_STATUS_OFF:
                str = "GPS关闭，建议开启GPS，提高定位质量";
                speck("GPS信号弱");
                break;
            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
                speck("GPS信号弱");
                break;
            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
                str = "没有GPS定位权限，建议开启gps定位权限";
                speck("GPS信号弱");
                break;
        }
        LogUtils.d(TAG, str);
    }
}
