package com.kborid.smart.manager;

import com.amap.api.location.AMapLocation;

public class LocationManagers {
    private static LocationInfo locationInfo;

    public static void setLocationInfo(AMapLocation location) {
        if (null != location) {
            locationInfo = new LocationInfo(location);
        }
    }

    public static String print() {
        return locationInfo.toString();
    }
}
