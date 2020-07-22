package com.kborid.setting.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.thunisoft.common.tool.UIHandler;

import java.util.Random;

public class MainViewModel extends ViewModel {

    public final MutableLiveData<String> title = new MutableLiveData<>();

    public MainViewModel() {
        title.setValue("快，点我！");
    }

    public void onClick() {
        Random random = new Random();
        int value = random.nextInt(100);
        if (value == 88)
            title.setValue("Congratulation~~!");
        else
            title.setValue(String.valueOf(value));
    }

    public void onAsyncClick() {
        final Random random = new Random();
        UIHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                title.setValue(random.nextInt(100) + "async");
            }
        }, 1000);
    }

}