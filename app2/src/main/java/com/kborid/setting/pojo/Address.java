package com.kborid.setting.pojo;

import androidx.lifecycle.MutableLiveData;

public class Address {
    private MutableLiveData<String> test = new MutableLiveData<>();
    private String province;
    private String city;
    private String p;

    public Address(String province, String city) {
        this.province = province;
        this.city = city;
        this.p = province + ", " + city;
    }

    public MutableLiveData<String> getTest() {
        return test;
    }

    public void setTest(MutableLiveData<String> test) {
        this.test = test;
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

    public String getP() {
        return p;
    }

    @Override
    public String toString() {
        return "AddressInfo{" +
                "province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", p='" + p + '\'' +
                '}';
    }
}
