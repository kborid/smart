package com.thunisoft.common.network.util;

import com.orhanobut.logger.Logger;
import com.thunisoft.common.network.comm.CommResBean;
import com.thunisoft.common.network.func.ApiException;
import com.thunisoft.common.network.func.NetworkException;
import com.thunisoft.common.network.func.ServiceException;
import com.thunisoft.common.util.ToastUtils;

import java.io.EOFException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.cert.CertPathValidatorException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.observers.LambdaObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * RxUtil
 *
 * @description: 统一处理
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2020/8/28
 */
public class RxUtil {
    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一返回结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<CommResBean<T>, T> handleResult() {   //compose判断结果
        return new ObservableTransformer<CommResBean<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<CommResBean<T>> upstream) {
                return upstream.flatMap(new Function<CommResBean<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(CommResBean<T> resBean) throws Exception {
                        if (ApiException.SUCCESS == resBean.getCode() || ApiException.OK == resBean.getCode()) {
                            return RxUtil.createData(resBean.getData());
                        } else {
                            return Observable.error(new ApiException(resBean.getMsg()));
                        }
                    }
                });
            }
        };
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        });
    }

    /**
     * 创建默认的订阅者
     *
     * @param onNext 数据处理方法
     * @param <T>
     * @return
     */
    public static <T> Observer<T> createDefaultSubscriber(final Consumer<? super T> onNext) {
        return createDefaultSubscriber(onNext, Functions.EMPTY_ACTION);
    }

    /**
     * 创建默认的订阅者
     *
     * @param onNext  数据处理方法
     * @param onFinal 最终执行方法（正常/异常都调用）
     * @param <T>
     * @return
     */
    public static <T> Observer<T> createDefaultSubscriber(final Consumer<? super T> onNext, Action onFinal) {
        return createDefaultSubscriber(onNext, createDefaultErrorHandler(onFinal), new Action() {
            @Override
            public void run() throws Exception {
                if (null != onFinal) {
                    onFinal.run();
                }
            }
        });
    }

    /**
     * 创建默认的订阅者
     *
     * @param onNext      数据处理方法
     * @param onError     错误处理
     * @param onCompleted 数据全部正常处理完成后调用方法
     * @param <T>
     * @return
     */
    public static <T> Observer<T> createDefaultSubscriber(final Consumer<? super T> onNext, final Consumer<Throwable> onError, final Action onCompleted) {
        ObjectHelper.requireNonNull(onNext, "onNext is null");
        ObjectHelper.requireNonNull(onError, "onError is null");
        ObjectHelper.requireNonNull(onCompleted, "onComplete is null");
        return new LambdaObserver<T>(onNext, onError, onCompleted, Functions.emptyConsumer());
    }

    /**
     * 创建默认的异常处理函数
     *
     * @param action 异常处理完成后要调用的函数
     * @return 异常处理函数
     */
    public static Consumer<Throwable> createDefaultErrorHandler(Action action) {
        return createDefaultErrorHandler(action, null);
    }

    public static Consumer<Throwable> createDefaultErrorHandler(Action action, final Function<Throwable, Boolean> func) {
        return new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                try {
                    if (func != null) {
                        if (func.apply(throwable)) {
                            return;
                        }
                    }

                    if (throwable instanceof ApiException) {
                        ToastUtils.showToast(throwable.getMessage());
                    } else if (throwable instanceof ServiceException) {
                        ToastUtils.showToast(throwable.getMessage());
                    } else if (throwable instanceof NetworkException) {
                        ToastUtils.showToast(throwable.getMessage());
                    } else if (throwable instanceof HttpException) {
                        HttpException httpException = (HttpException) throwable;
                        if (httpException.code() == 401) {
                            ToastUtils.showToast("请重新登录");
                        } else if (httpException.code() == 304) {
                            Logger.i("数据未更新（304）");
                        } else {
                            ToastUtils.showToast("网络异常(" + httpException.code() + ")");
                        }
                    } else if (throwable instanceof SocketTimeoutException) {
                        ToastUtils.showToast("网络连接超时,请检查网络");
                    } else if (throwable instanceof ConnectException | throwable instanceof SocketException) {
                        ToastUtils.showToast("无法连接到服务器,请检查网络");
                    } else if (throwable instanceof UnknownHostException) {
                        ToastUtils.showToast("无法解析服务器地址");
                    } else if (throwable instanceof CertPathValidatorException) {
                        ToastUtils.showToast("HTTPS证书验证不通过");
                        Logger.e(throwable, "未知系统错误");
                    } else if (throwable instanceof SSLHandshakeException) {
                        ToastUtils.showToast("SSL握手失败");
                        Logger.e(throwable, "未知系统错误");
                    } else if (throwable instanceof EOFException) {
                        ToastUtils.showToast("网络访问异常，服务器端关闭连接");
                        Logger.e(throwable, "未知系统错误");
                    } else {
                        if (throwable != null) {
                            // 暂时去掉提示
                            // ToastUtils.showToast("未知系统错误");
                            Logger.e(throwable, "未知系统错误");
                        }
                    }
                } finally {
                    if (action != null) {
                        action.run();
                    }
                }
            }
        };
    }
}
