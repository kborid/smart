package com.kborid.setting.network.intercept;

import androidx.annotation.NonNull;
import com.thunisoft.common.network.util.RxUtil;
import com.thunisoft.common.util.SPUtils;
import io.reactivex.Observable;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * ReceivedCookiesInterceptor
 *
 * @description: 保存Cookie拦截器
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2021/1/20
 */
public class ReceivedCookiesInterceptor implements Interceptor {


    public ReceivedCookiesInterceptor() {
    }

    @NonNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            final StringBuffer cookieBuffer = new StringBuffer();
            //最近在学习RxJava,这里用了RxJava的相关API大家可以忽略,用自己逻辑实现即可.大家可以用别的方法保存cookie数据
            Observable.fromIterable(originalResponse.headers("Set-Cookie"))
                    .map(s -> {
                        String[] cookieArray = s.split(";");
                        return cookieArray[0];
                    })
                    .subscribe(RxUtil.createDefaultSubscriber(
                            s -> cookieBuffer.append(s).append(";")));
            System.out.println("cookie = " + cookieBuffer.toString());
            SPUtils.setString("cookie", cookieBuffer.toString());
        }

        return originalResponse;
    }
}
