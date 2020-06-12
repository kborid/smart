package com.kborid.setting.demo.t_rxjava;

import android.annotation.SuppressLint;

import com.kborid.setting.demo.t_okhttp.OkHttpHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.functions.Function;

public class RxJavaTest {

    private static final Logger logger = LoggerFactory.getLogger(RxJavaTest.class);

    private static final String COMMA = ",";

    @SuppressLint("CheckResult")
    public static void test() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                logger.info("Observable created.【{}】", Thread.currentThread().getName());
                emitter.onNext("1");
                emitter.onNext("2");
            }
        })
                .compose(new ObservableTransformer<String, String>() {
                    @Override
                    public ObservableSource<String> apply(Observable<String> upstream) {
                        return upstream.flatMap(new Function<String, ObservableSource<String>>() {
                            @Override
                            public ObservableSource<String> apply(String s) throws Exception {
                                return new ObservableSource<String>() {
                                    @Override
                                    public void subscribe(Observer<? super String> observer) {
                                        observer.onNext("他妈的," + s + ",是真神奇");
                                    }
                                };
                            }
                        });
                    }
                })
                .compose(OkHttpHelper.rxSchedulerIo())
                .flatMap((Function<String, ObservableSource<String>>) o -> {
                    logger.info("Observable flatmap. Receiver:{}", o);
                    logger.info("Observable flatmap.【{}】", Thread.currentThread().getName());
                    return (ObservableSource<String>) observer -> {
                        logger.info("Observable flatmap reobservable.【{}】", Thread.currentThread().getName());
                        String[] ret = o.split(COMMA);
                        ret[1] += "000";
                        observer.onNext(Arrays.toString(ret));

                    };
                })
                .compose(OkHttpHelper.rxSchedulerComputation())
                .doOnNext(s -> {
                    logger.info("Observer doOnNext. Receiver:{}", s);
                    logger.info("Observer doOnNext.【{}】", Thread.currentThread().getName());
                })
                .doAfterNext(s -> {
                    logger.info("Observer doAfterNext. Receiver:{}", s);
                    logger.info("Observer doAfterNext.【{}】", Thread.currentThread().getName());
                })
                .compose(OkHttpHelper.rxSchedulerMain())
                .subscribe(o -> {
                    logger.info("Observer Consumer. Receiver:{}", o);
                    logger.info("Observer Consumer.【{}】", Thread.currentThread().getName());
                });
    }
}
