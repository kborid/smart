package com.kborid.demo.t_okhttp;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.kborid.setting.BuildConfig;
import com.kborid.setting.network.intercept.AddCookiesInterceptor;
import com.kborid.setting.network.intercept.ReceivedCookiesInterceptor;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import okhttp3.Authenticator;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OkHttpHelper
 *
 * @description: okhttp工具类
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2020/3/19
 */
public class OkHttpHelper {

    private final static Logger log = LoggerFactory.getLogger(OkHttpHelper.class);

    private OkHttpClient okHttpClient;

    private static OkHttpHelper instance = null;

    public static OkHttpHelper getInstance() {
        if (null == instance) {
            instance = new OkHttpHelper();
        }
        return instance;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }

    private OkHttpHelper() {
        if (null == okHttpClient) {
            Authenticator authenticator = (route, response) -> response.request().newBuilder()
                    .header("Authorization", "token")
                    .build();
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .authenticator(authenticator)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS);

            builder.addInterceptor(new ReceivedCookiesInterceptor());
            builder.addInterceptor(new AddCookiesInterceptor());
//            builder.addInterceptor(
//                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS));
            builder.addNetworkInterceptor(
                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS));
            if (BuildConfig.DEBUG) {
                builder.addNetworkInterceptor(new StethoInterceptor());
            }
            okHttpClient = builder.build();
        }
    }

    /**
     * 同步get
     *
     * @param url 请求地址
     * @return 响应体
     */
    public synchronized Response syncGet(String url) {
        Response res = null;
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.lock();
            Request req = new Request.Builder().url(url).tag(url).build();
            res = okHttpClient.newCall(req).execute();
            if (res.isSuccessful()) {
                return res;
            }
        } catch (Exception e) {
            log.error("同步GET请求失败", e);
        } finally {
            lock.unlock();
        }
        return res;
    }

    /**
     * 异步get
     *
     * @param url 请求地址
     * @param callback 异步回调
     */
    public void asyncGet(String url, Callback callback) {
        Request req = new Request.Builder().url(url).tag(url).build();
        okHttpClient.newCall(req).enqueue(callback);
    }

    /**
     * 同步post Json请求
     *
     * @param url 请求地址
     * @param json 请求参数
     * @return 响应体
     */
    public Response syncPostJson(String url, String json) {
        Response res = null;
        RequestBody reqBody = RequestBody
                .create(MediaType.parse("application/json; charset=utf-8"), json);
        Request req = new Request.Builder().url(url).tag(url).post(reqBody).build();
        try {
            res = okHttpClient.newCall(req).execute();
        } catch (Exception e) {
            log.error("同步POST请求失败", e);
        }
        return res;
    }

    /**
     * 同步post 表单请求
     *
     * @param url 请求地址
     * @param params 请求参数
     * @return res响应
     */
    public Response syncPostForm(String url, Map<String, String> params) {
        Response res = null;
        if (null != params) {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : params.keySet()) {
                builder.add(key, Objects.requireNonNull(params.get(key)));
            }
            Request req = new Request.Builder().url(url).tag(url).post(builder.build()).build();
            try {
                res = okHttpClient.newCall(req).execute();
            } catch (Exception e) {
                log.error("同步POST表单请求失败", e);
            }
        }
        return res;
    }

    /**
     * 全部任务取消
     */
    public void cancelAll() {
        if (null != okHttpClient) {
            okHttpClient.dispatcher().cancelAll();
        }
    }
}
