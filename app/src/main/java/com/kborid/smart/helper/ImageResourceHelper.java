package com.kborid.smart.helper;

import java.util.ArrayList;
import java.util.List;

public class ImageResourceHelper {
    private static List<String> sImageUrlList = null;

    public static List<String> getImages() {
        if (null != sImageUrlList) {
            return sImageUrlList;
        }

        sImageUrlList = new ArrayList<>();
        String url = "http://image2.sina.com.cn/ent/d/2005-06-21/U105P28T3D758537F326DT20050621155831.jpg";
        for (int i = 1; i <= 15; i++) {
            sImageUrlList.add(url);
        }
        return sImageUrlList;
    }
}
