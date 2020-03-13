package com.kborid.smart.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.kborid.smart.PRJApplication;
import com.orhanobut.logger.Logger;

public enum NativeLocationManager {
    INSTANCE;

    private static final String TAG = "NativeLocationManager";
    private static final int TWO_MINUTES = 1000 * 60 * 2;

    public static final String GPS_PROVIDER = LocationManager.GPS_PROVIDER;
    public static final String NET_PROVIDER = LocationManager.NETWORK_PROVIDER;
    private final LocationManager locationManager = (LocationManager) PRJApplication.getInstance().getSystemService(Context.LOCATION_SERVICE);

    private Location mCurrLocation = null;

    private LocationChangedListener locationChangedListener = null;

    private LocationListener internalLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Logger.t(TAG).d("onLocationChanged() location = " + location);
            if (null != location) {
                if (null == mCurrLocation || isBetterLocation(location, mCurrLocation)) {
                    mCurrLocation = location;
                }
            }
            if (null != locationChangedListener) {
                locationChangedListener.onLocationChanged(mCurrLocation);
            }
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
    };

    private GpsStatus.Listener gpsStatusListener = new GpsStatus.Listener() {
        @Override
        public void onGpsStatusChanged(int event) {
            Logger.t(TAG).d("onGpsStatusChanged() event:" + event);
//            switch (event) {
//                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
//                    GpsStatus gpsStatus = locationManager.getGpsStatus(null);
//                    Iterable<GpsSatellite> it = gpsStatus.getSatellites();
//                    break;
//                default:
//                    break;
//            }
        }
    };

    @SuppressLint("MissingPermission")
    public void startLocation(LocationChangedListener listener) {
        locationChangedListener = listener;
        if (locationManager.isProviderEnabled(GPS_PROVIDER)) {
            mCurrLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        locationManager.addGpsStatusListener(gpsStatusListener);
        locationManager.requestLocationUpdates(GPS_PROVIDER, TWO_MINUTES, 0, internalLocationListener);
        locationManager.requestLocationUpdates(NET_PROVIDER, TWO_MINUTES, 0, internalLocationListener);
    }

    public void stopLocation() {
        locationChangedListener = null;
        locationManager.removeGpsStatusListener(gpsStatusListener);
    }

    /**
     * Determines whether one Location reading is better than the current Location fix
     *
     * @param location            The new Location that you want to evaluate
     * @param currentBestLocation The current Location fix, to which you want to compare the new one
     */
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return true;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use
        // the new location
        // because the user has likely moved
        if (isSignificantlyNewer) {
            return true;
            // If the new location is more than two minutes older, it must be
            // worse
        } else if (isSignificantlyOlder) {
            return false;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // Check if the old and new location are from the same provider
        boolean isFromSameProvider = isSameProvider(location.getProvider(), currentBestLocation.getProvider());

        // Determine location quality using a combination of timeliness and
        // accuracy
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether two providers are the same
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }
}
