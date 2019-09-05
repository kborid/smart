package com.kborid.smart.fragment.first.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FirstModel {

    public static final String KEY_NAME = "key_name";

    private List<HashMap<String, String>> mData = new ArrayList<>();

    public void requestData(int size) {
        for (int i = 0; i < size; i++) {
            HashMap<String, String> item = new HashMap<>();
            item.put(KEY_NAME, String.valueOf(i));
            mData.add(item);
        }
    }

    public List<HashMap<String, String>> getData() {
        return mData;
    }

    public void removeData(int index) {
        if (mData.size() >= index) {
            mData.remove(index);
        }
    }
}
