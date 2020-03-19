package com.kborid.setting.t_okhttp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

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

    private static final String TAG = "req_tag";
    private OkHttpClient okHttpClient;

    private static OkHttpHelper instance = null;

    public static OkHttpHelper getInstance() {
        if (null == instance) {
            instance = new OkHttpHelper();
        }
        return instance;
    }

    private OkHttpHelper() {
        if (null == okHttpClient) {
            Authenticator authenticator = new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    return response.request().newBuilder()
                            .header("Authorization", "token")
                            .build();
                }
            };
            okHttpClient = new OkHttpClient.Builder()
                    .authenticator(authenticator)
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
    }

    /**
     * 同步get
     *
     * @param url
     * @return
     */
    public Response syncGet(String url) {
        Request req = new Request.Builder().url(url).tag(TAG).build();
        try {
            Response res = okHttpClient.newCall(req).execute();
            if (res.isSuccessful()) {
                return res;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 异步get
     *
     * @param url
     * @param callback
     */
    public void asyncGet(String url, Callback callback) {
        Request req = new Request.Builder().url(url).tag(TAG).build();
        okHttpClient.newCall(req).enqueue(callback);
    }

    /**
     * 同步post Json请求
     *
     * @param url
     * @param json
     * @return
     */
    public Response syncPostJson(String url, String json) {
        RequestBody reqBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request req = new Request.Builder().url(url).tag(TAG).post(reqBody).build();
        try {
            Response res = okHttpClient.newCall(req).execute();
            if (res.isSuccessful()) {
                return res;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 同步post 表单请求
     *
     * @param url
     * @param params
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Response syncPostForm(String url, Map<String, String> params) {
        if (null != params) {
            FormBody.Builder builder = new FormBody.Builder();
            for (String key : params.keySet()) {
                builder.add(key, params.getOrDefault(key, ""));
            }
            Request req = new Request.Builder().url(url).tag(TAG).post(builder.build()).build();
            try {
                Response res = okHttpClient.newCall(req).execute();
                if (res.isSuccessful()) {
                    return res;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 打印响应头信息
     *
     * @param response
     */
    public void printResHeader(Response response) {
        if (null != response) {
            Headers rh = response.headers();
            for (int i = 0; i < rh.size(); i++) {
                System.out.println(rh.name(i) + " : " + rh.value(i));
            }
        }
    }

    /**
     * 打印响应体信息
     *
     * @param response
     */
    public void printResBody(Response response) {
        if (null != response && null != response.body()) {
            try {
                System.out.println(response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO
    public void cancel() {

    }
}
