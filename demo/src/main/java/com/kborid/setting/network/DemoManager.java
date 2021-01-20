package com.kborid.setting.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.kborid.demo.t_okhttp.OkHttpHelper;
import com.kborid.setting.entity.WsVO;
import com.kborid.setting.network.convert.CustomConverterFactory;
import com.thunisoft.common.network.util.RxUtil;
import io.reactivex.Observable;
import java.lang.reflect.Type;
import java.util.List;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * DemoManager
 *
 * @description: retrofit接口管理类
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2021/1/20
 */
public final class DemoManager {

    private final static String BASE_URL = "http://172.25.13.4:9000/";

    private DemoManager() {
    }

    private static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addConverterFactory(CustomConverterFactory.create())
                .client(OkHttpHelper.getInstance().getOkHttpClient())
                .baseUrl(BASE_URL)
                .build();
    }

    private static DemoApi getApi() {
        return getRetrofit().create(DemoApi.class);
    }

    public static Observable<String> getUserInfo(String id) {
        return getApi().getUserInfo(id);
    }

    public static Call<String> getUserInfo2(String id) {
        return getApi().getUserInfo2(id);
    }

    public static Observable<List<WsVO>> getWsListByMbbh(String mbbh) {
        return getApi().getWsListByMbbh(mbbh).compose(RxUtil.rxSchedulerMain());
    }

    public static Gson buildGson() {
        return new GsonBuilder()
                .serializeNulls()
                .registerTypeAdapter(int.class, new GsonIntegerDefaultAdapter())
                .create();
    }

    public static class GsonIntegerDefaultAdapter implements JsonSerializer<Integer>,
            JsonDeserializer<Integer> {

        @Override
        public Integer deserialize(JsonElement json, Type typeOfT,
                JsonDeserializationContext context)
                throws JsonParseException {
            try {
                //定义为int类型,如果后台返回""或者null,则返回0
                if ("".equals(json.getAsString()) || "null".equals(json.getAsString())) {
                    return 0;
                }
            } catch (Exception ignore) {
                // ignore
            }
            try {
                return json.getAsInt();
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override
        public JsonElement serialize(Integer src, Type typeOfSrc,
                JsonSerializationContext context) {
            return new JsonPrimitive(src);
        }
    }
}
