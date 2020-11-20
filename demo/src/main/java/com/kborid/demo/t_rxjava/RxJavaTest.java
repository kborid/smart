package com.kborid.demo.t_rxjava;

import com.thunisoft.common.network.util.RxUtil;
import io.reactivex.*;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;

public class RxJavaTest {

    private static final Logger logger = LoggerFactory.getLogger(RxJavaTest.class);

    private static final String COMMA = ",";

    public static void test() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                logger.info("Observable created.【{}】", Thread.currentThread().getName());
                emitter.onNext("1");
                emitter.onNext("2");
                Optional.ofNullable(emitter).ifPresent(new java.util.function.Consumer<ObservableEmitter<String>>() {
                    @Override
                    public void accept(ObservableEmitter<String> stringObservableEmitter) {

                    }
                });
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
                    logger.info("Observable flatmap.【{}】", Thread.currentThread().getName());
                    return (ObservableSource<String>) observer -> {
                        logger.info("Observable flatmap reobservable.【{}】", Thread.currentThread().getName());
                        String[] ret = o.split(COMMA);
                        ret[1] += "000";
                        observer.onNext(Arrays.toString(ret));

                    };
                })
                .compose(RxUtil.rxSchedulerComputation())
                .doOnNext(s -> {
                    logger.info("Observer doOnNext. Receiver:{}", s);
                    logger.info("Observer doOnNext.【{}】", Thread.currentThread().getName());
                })
                .doAfterNext(s -> {
                    logger.info("Observer doAfterNext. Receiver:{}", s);
                    logger.info("Observer doAfterNext.【{}】", Thread.currentThread().getName());
                })
                .compose(RxUtil.rxSchedulerMain())
                .subscribe(RxUtil.createDefaultSubscriber(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        logger.info("Observer Consumer. Receiver:{}", s);
                        logger.info("Observer Consumer.【{}】", Thread.currentThread().getName());
                    }
                }));
    }
}
