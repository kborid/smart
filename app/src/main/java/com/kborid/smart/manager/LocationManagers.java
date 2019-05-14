package com.kborid.smart.manager;

public class LocationManagers {
    public static double lon = 0;
    public static double lat = 0;
    public static String city = "";
    public static String cityCode = "";

    public static String print() {
        return "city = " + city + ", cityCode = " + cityCode + ", lon = " + lon + ", lat = " + lat;
    }
}
