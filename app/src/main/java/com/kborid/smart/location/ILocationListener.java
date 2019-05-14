package com.kborid.smart.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.orhanobut.logger.Logger;

public class ILocationListener implements LocationListener {

    private static final String TAG = ILocationListener.class.getSimpleName();

    @Override
    public void onLocationChanged(Location location) {
        Logger.t(TAG).d("onLocationChanged() " + location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Logger.t(TAG).d("onStatusChanged() " + provider + ", " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Logger.t(TAG).d("onProviderEnabled() " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Logger.t(TAG).d("onProviderDisabled() " + provider);
    }
}
