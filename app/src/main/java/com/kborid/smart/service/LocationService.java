package com.kborid.smart.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.AMapLocationQualityReport;
import com.kborid.library.common.UIHandler;
import com.kborid.library.util.LogUtils;
import com.kborid.smart.R;
import com.kborid.smart.activity.MainActivity;
import com.kborid.smart.manager.LocationManager;
import com.orhanobut.logger.Logger;

/**
 * 高德定位Service
 */
public class LocationService extends Service implements AMapLocationListener {

    private static final String CHANNEL_ID = LocationService.class.getName();
    private static final String CHANNEL_NAME = LocationService.class.getName();

    private static final String TAG = "LocationService";
    private static final int DURING_CHECK = 10000;
    private static final int DELAY_TIME = 10000;
    private AMapLocationClient mLocationClient = null;
    private AMapLocation mAMapLocation;

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
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(false);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setInterval(5000);
        mLocationClient.setLocationOption(mLocationOption);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UIHandler.getHandler().removeCallbacksAndMessages(0);
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
        stopForeground(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setShowBadge(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(notificationChannel);
            }
        }

        Notification.Builder builder = new Notification.Builder(this.getApplicationContext());
        Intent nfIntent = new Intent(this, MainActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0)) // 设置PendingIntent
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
                .setContentTitle("下拉列表中的Title") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                .setContentText("要显示的内容") // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }
        Notification notification = builder.build(); // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
        startForeground(110, notification);
        mLocationClient.startLocation();
        UIHandler.postDelayed(checkGpsRunnable, DELAY_TIME);
        return super.onStartCommand(intent, flags, startId);
    }

    private Runnable checkGpsRunnable = new Runnable() {
        @Override
        public void run() {
            if (mAMapLocation != null) {
                LogUtils.d(TAG, "检查GPS信号");
                parseGPSStatusString(mAMapLocation.getLocationQualityReport().getGPSStatus());
                UIHandler.postDelayed(checkGpsRunnable, DURING_CHECK);
            }
        }
    };

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Logger.t(TAG).d(aMapLocation);
        mAMapLocation = aMapLocation;
        if (aMapLocation != null) {
            LocationManager.lon = aMapLocation.getLongitude();
            LocationManager.lat = aMapLocation.getLatitude();
            LocationManager.city = aMapLocation.getCity();
            LocationManager.cityCode = aMapLocation.getCityCode();
            LogUtils.d(TAG, LocationManager.print());
        }
    }

    public static void startLocationService(Context context) {
        Intent intent = new Intent(context, LocationService.class);
        context.startService(intent);
    }

    public void speck(String str) {
        LogUtils.d(TAG, str);
    }

    /**
     * 解析GPS状态的
     *
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
    }
}
