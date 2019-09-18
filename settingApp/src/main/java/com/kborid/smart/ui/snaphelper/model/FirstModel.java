package com.kborid.smart.ui.snaphelper.model;

import com.kborid.smart.ui.snaphelper.AppInfo;

import java.util.ArrayList;
import java.util.List;

public class FirstModel {

    public static final String KEY_NAME = "key_name";

    private List<AppInfo> mData = new ArrayList<>();

    public void requestData(int size) {
        String url1 = "https://t1.ituba.cc/mm8/tupai/201511/195/%1$d.jpg";
        String url2 = "https://t1.ituba.cc/mm8/tupai/201511/136/%1$d.jpg";
        String url3 = "https://t1.ituba.cc/mm8/tupai/201511/120/%1$d.jpg";
        String url4 = "https://t1.ituba.cc/mm8/tupai/201511/105/%1$d.jpg";
        String url5 = "https://t1.ituba.cc/mm8/tupai/201511/109/%1$d.jpg";
        String url6 = "https://t1.ituba.cc/mm8/tupai/201511/116/%1$d.jpg";
        for (int i = 1; i <= 15; i++) {
            AppInfo appInfo1 = new AppInfo();
            appInfo1.setIconUrl(String.format(url1, i));
            mData.add(appInfo1);
            AppInfo appInfo2 = new AppInfo();
            appInfo2.setIconUrl(String.format(url2, i));
            mData.add(appInfo2);
            AppInfo appInfo3 = new AppInfo();
            appInfo3.setIconUrl(String.format(url3, i));
            mData.add(appInfo3);
            AppInfo appInfo4 = new AppInfo();
            appInfo4.setIconUrl(String.format(url4, i));
            mData.add(appInfo4);
            AppInfo appInfo5 = new AppInfo();
            appInfo5.setIconUrl(String.format(url5, i));
            mData.add(appInfo5);
            AppInfo appInfo6 = new AppInfo();
            appInfo6.setIconUrl(String.format(url6, i));
            mData.add(appInfo6);
        }
    }

    public List<AppInfo> getData() {
        return mData;
    }

    public void removeData(int index) {
        if (mData.size() >= index) {
            mData.remove(index);
        }
    }
}
