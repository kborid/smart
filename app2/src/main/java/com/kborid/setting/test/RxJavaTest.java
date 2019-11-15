package com.kborid.setting.test;

import android.annotation.SuppressLint;
import android.os.Looper;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaTest {
    @SuppressLint("CheckResult")
    private void test() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                System.out.println("3=" + Thread.currentThread().getName());
                emitter.onNext("1");
                emitter.onNext("2");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String o) throws Exception {
                        System.out.println("flatmap=" + o);
                        System.out.println("5=" + Thread.currentThread().getName());
                        return new ObservableSource<String>() {
                            @Override
                            public void subscribe(Observer<? super String> observer) {
                                System.out.println("1=" + Thread.currentThread().getName());
                                observer.onNext("3");
                            }
                        };
                    }
                })
                .observeOn(Schedulers.computation())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("doOnNext" + s);
                        System.out.println("3=" + Thread.currentThread().getName());
                        if (Looper.myLooper() == Looper.getMainLooper()) {
                            System.out.println("main");
                        }
                    }
                })
                .doAfterNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println("doAfterNext" + s);
                        System.out.println("4=" + Thread.currentThread().getName());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        System.out.println("subscribe" + o);
                        System.out.println("2=" + Thread.currentThread().getName());
                    }
                });
    }
}
