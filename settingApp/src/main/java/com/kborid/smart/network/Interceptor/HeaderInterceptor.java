package com.kborid.smart.network.Interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private static final String DEFAULT_AGENT = "Mozilla/5.0 (Linux;Android "
            + android.os.Build.VERSION.RELEASE + "; " + android.os.Build.MODEL
            + ") AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/45.0.2454.95 Mobile Safari/537.36";

    @Override
    public Response intercept(Chain chain) throws IOException {
        try {
            Request request = chain.request()
                    .newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("User-Agent", DEFAULT_AGENT)
                    .addHeader("t.tt", "mememe")
                    .build();
            return chain.proceed(request);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
