package com.kborid.smart.location;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.orhanobut.logger.Logger;

public enum LocalManager {
    INSTANCE;

    private static final String TAG = "LocalManager";

    private static final int TWO_MINUTES = 1000 * 60 * 2;
    public static final String GPS_PROVIDER = LocationManager.GPS_PROVIDER;
    public static final String NET_PROVIDER = LocationManager.NETWORK_PROVIDER;

    private Location mLocation = null;

    @SuppressLint("MissingPermission")
    public Location startGPSLocation(Context context) {
        LocationManager manager = getLocationManagerService(context);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            mLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        return mLocation;
    }

    private LocationManager getLocationManagerService(@NonNull Context context) {
        return (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * Determines whether one Location reading is better than the current Location fix
     *
     * @param location The new Location that you want to evaluate
     * @param currentBestLocation The current Location fix, to which you want to compare the new one
     */
    protected boolean isBetterLocation(Location location, Location currentBestLocation)
    {
        if (currentBestLocation == null)
        {
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
        if (isSignificantlyNewer)
        {
            return true;
            // If the new location is more than two minutes older, it must be
            // worse
        }
        else if (isSignificantlyOlder)
        {
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
        if (isMoreAccurate)
        {
            return true;
        }
        else if (isNewer && !isLessAccurate)
        {
            return true;
        }
        else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider)
        {
            return true;
        }
        return false;
    }

    /** Checks whether two providers are the same */
    private boolean isSameProvider(String provider1, String provider2)
    {
        if (provider1 == null)
        {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

    @SuppressLint("MissingPermission")
    public void registerLocationListener(Context context) {
        final LocationManager manager = getLocationManagerService(context);
        manager.requestLocationUpdates(GPS_PROVIDER, 2000, 1, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                if (null != location) {
                    mLocation = location;
                }
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
        });
        manager.addGpsStatusListener(new GpsStatus.Listener() {
            @Override
            public void onGpsStatusChanged(int event) {
                Logger.t(TAG).d("onGpsStatusChanged() event:" + event);
                Logger.t(TAG).d("onLocationChanged() " + mLocation);
                switch (event) {
                    case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                        GpsStatus gpsStatus = manager.getGpsStatus(null);
                        Iterable<GpsSatellite> it = gpsStatus.getSatellites();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
