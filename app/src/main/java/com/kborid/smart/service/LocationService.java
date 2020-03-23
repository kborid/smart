package com.kborid.smart.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.kborid.smart.R;
import com.kborid.smart.location.LocationChangedListener;
import com.kborid.smart.location.NativeLocationManager;
import com.kborid.smart.ui.MainFragmentActivity;
import com.orhanobut.logger.Logger;
import com.thunisoft.common.tool.UIHandler;
import com.thunisoft.common.util.ToastUtils;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * 高德定位Service
 */
public class LocationService extends Service implements LocationChangedListener {

    private static final String CHANNEL_ID = "CHANNEL_ID_001";
    private static final String CHANNEL_NAME = "CHANNEL_NAME_001";

    private static final String TAG = "LocationService";
    private static final int DURING_TIME = 60000;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        NativeLocationManager.INSTANCE.stopLocation();
        UIHandler.getHandler().removeCallbacksAndMessages(null);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setServiceForeground();
        NativeLocationManager.INSTANCE.startLocation(this);
        UIHandler.postDelayed(checkGpsRunnable, DURING_TIME);
        return super.onStartCommand(intent, flags, startId);
    }

    private void setServiceForeground() {
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(notificationChannel);
            }
            builder.setChannelId(CHANNEL_ID);
        }

        Intent nfIntent = new Intent(this, MainFragmentActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0)) // 设置PendingIntent
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.logo)) // 设置下拉列表中的图标(大图标)
                .setContentTitle("正在进行后台定位") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.logo) // 设置状态栏内的小图标
                .setContentText("点击查看更多") // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间
        Notification notification = builder.build(); // 获取构建好的Notification

        startForeground(1101, notification);
    }

    private Runnable checkGpsRunnable = new Runnable() {
        @Override
        public void run() {
            UIHandler.postDelayed(checkGpsRunnable, DURING_TIME * 2L);
        }
    };

    public static void startLocationService(Context context) {
        Intent intent = new Intent(context, LocationService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (null != location) {
            ToastUtils.showToast(getLocationAddress(location));
        }
    }

    public void speck(String str) {
        Logger.t(TAG).d(str);
    }

    /**
     * 解析GPS状态的
     *
     * @param statusCode GPS状态码
     * @return
     */
//    private void parseGPSStatusString(int statusCode) {
//        String str = "";
//        switch (statusCode) {
//            case AMapLocationQualityReport.GPS_STATUS_OK:
//                str = "GPS状态正常";
//                speck("GPS信号正常");
//                break;
//            case AMapLocationQualityReport.GPS_STATUS_NOGPSPROVIDER:
//                str = "手机中没有GPS Provider，无法进行GPS定位";
//                speck("GPS信号弱");
//                break;
//            case AMapLocationQualityReport.GPS_STATUS_OFF:
//                str = "GPS关闭，建议开启GPS，提高定位质量";
//                speck("GPS信号弱");
//                break;
//            case AMapLocationQualityReport.GPS_STATUS_MODE_SAVING:
//                str = "选择的定位模式中不包含GPS定位，建议选择包含GPS定位的模式，提高定位质量";
//                speck("GPS信号弱");
//                break;
//            case AMapLocationQualityReport.GPS_STATUS_NOGPSPERMISSION:
//                str = "没有GPS定位权限，建议开启gps定位权限";
//                speck("GPS信号弱");
//                break;
//                default:
//                    Logger.t(TAG).d("no defined");
//                    break;
//        }
//        Logger.t(TAG).d(str);
//    }

    /**
     * 将经纬度转换成中文地址
     *
     * @param location
     * @return
     */
    private String getLocationAddress(Location location) {
        String add = "";
        Geocoder geoCoder = new Geocoder(getBaseContext(), Locale.CHINESE);
        try {
            List<Address> addresses = geoCoder.getFromLocation(
                    location.getLatitude(), location.getLongitude(),
                    1);
            Address address = addresses.get(0);
            Log.i(TAG, "getLocationAddress: " + address.toString());
            // Address[addressLines=[0:"中国",1:"北京市海淀区",2:"华奥饭店公司写字间中关村创业大街"]latitude=39.980973,hasLongitude=true,longitude=116.301712]
            int maxLine = address.getMaxAddressLineIndex();
            if (maxLine >= 2) {
                add = address.getAddressLine(0) + address.getAddressLine(1);
            } else {
                add = address.getAddressLine(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return add;
    }
}
