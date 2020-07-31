package com.kborid.library.base;

import android.app.Application;
import android.content.Context;

import com.kborid.library.util.LogUtils;
import com.thunisoft.common.ThunisoftCommon;
import com.thunisoft.common.tool.CrashHandler;
import com.thunisoft.ui.ThunisoftUI;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BaseApplication extends Application {

    private static final String TAG = BaseApplication.class.getSimpleName();

    protected static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init();
        LogUtils.init();
        LogUtils.d(TAG, "onCreate()");
        ThunisoftCommon.init(this);
        ThunisoftUI.init(this);
//        PackageManagerImpl.init(this);
    }

    protected static Observer<String> apply(Observable<String> observable, Observer<String> observer) {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("小马卧槽，Log onSubscribe()");
                System.out.println(observable);
                observer.onSubscribe(d);
            }

            @Override
            public void onNext(String o) {
                System.out.println("小马卧槽，Log onNext()");
                observer.onNext(o);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("小马卧槽，Log onError)");
                observer.onError(e);
            }

            @Override
            public void onComplete() {
                System.out.println("小马卧槽，Log onComplete)");
                observer.onComplete();
            }
        };
    }
}
