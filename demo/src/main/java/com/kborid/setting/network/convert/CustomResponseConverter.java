package com.kborid.setting.network.convert;


import java.io.IOException;
import javax.annotation.Nullable;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * CustomResponseConverter
 *
 * @description: 自定义响应转换器
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2021/1/20
 */
public class CustomResponseConverter<T> implements
        Converter<ResponseBody, T> {

    @Nullable
    @Override
    public T convert(ResponseBody value) throws IOException {
        return null;
    }
}