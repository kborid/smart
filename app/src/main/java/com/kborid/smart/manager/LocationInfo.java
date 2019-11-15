package com.kborid.smart.manager;

import com.amap.api.location.AMapLocation;

public class LocationInfo {
    private double lon, lat;
    private float speed;
    private String country;
    private String province;
    private String city;
    private String district;
    private String road;
    private String poiName;
    private String address;

    public LocationInfo() {
    }

    public LocationInfo(AMapLocation location) {
        country = location.getCountry();
        province = location.getProvince();
        city = location.getCity();
        district = location.getDistrict();
        poiName = location.getPoiName();
        road = location.getRoad();
        speed = location.getSpeed();
        lon = location.getLongitude();
        lat = location.getLatitude();
        address = location.getAddress();
    }

    @Override
    public String toString() {
        return "LocationInfo{" +
                "lon=" + lon +
                ", lat=" + lat +
                ", speed=" + speed +
                ", country='" + country + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", road='" + road + '\'' +
                ", poiName='" + poiName + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getPoiName() {
        return poiName;
    }

    public void setPoiName(String poiName) {
        this.poiName = poiName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
