package com.kborid.setting.network.intercept;

import com.thunisoft.common.network.util.RxUtil;
import com.thunisoft.common.util.SPUtils;
import io.reactivex.Observable;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * AddCookiesInterceptor
 *
 * @description: 添加Cookie拦截器
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2021/1/20
 */
public class AddCookiesInterceptor implements Interceptor {

    public AddCookiesInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        final Request.Builder builder = chain.request().newBuilder();

        //最近在学习RxJava,这里用了RxJava的相关API大家可以忽略,用自己逻辑实现即可
        Observable.just(SPUtils.getString("cookie", ""))
                .subscribe(RxUtil.createDefaultSubscriber(s -> {
                    //添加cookie
                    builder.addHeader("Cookie", s);
                }));
        return chain.proceed(builder.build());
    }
}