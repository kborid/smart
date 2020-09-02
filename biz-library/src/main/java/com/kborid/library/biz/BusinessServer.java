package com.kborid.library.biz;

import android.content.Context;

import com.kborid.library.base.BaseApplication;
import com.kborid.library.di.ContextLife;

import javax.inject.Inject;

public class BusinessServer {

    private Context context;

    @Inject
    public BusinessServer(@ContextLife BaseApplication context) {
        this.context = context;
    }

    public void test() {
        System.out.println(context);
    }
}
