package com.kborid.demo.t_rxjava;

import com.thunisoft.common.network.util.RxUtil;

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

    public static void test() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                logger.info("Observable created.【{}】", emitter);
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
                .compose(RxUtil.rxSchedulerIo())
                .flatMap((Function<String, ObservableSource<String>>) o -> {
                    logger.info("Observable flatmap. Receiver:{}", o);
                    return (ObservableSource<String>) observer -> {
                        String[] ret = o.split(COMMA);
                        ret[1] += "000";
                        observer.onNext(Arrays.toString(ret));

                    };
                })
                .compose(RxUtil.rxSchedulerComputation())
                .doOnNext(s -> logger.info("Observer doOnNext. Receiver:{}", s))
                .doAfterNext(s -> logger.info("Observer doAfterNext. Receiver:{}", s))
                .compose(RxUtil.rxSchedulerMain())
                .subscribe(RxUtil.createDefaultSubscriber(s -> logger.info("Observer Consumer. Receiver:{}", s)));
    }
}
