package com.kborid.library.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.kborid.library.R;

/**
 * Description : 图片加载工具类 使用glide框架封装
 */
public class ImageLoaderUtils {

    private static RequestOptions requestOptions = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
//            .error(R.mipmap.ic_loading_error)
            .placeholder(R.mipmap.ic_placeholder);

    public static void display(Context context, ImageView imageView, String url, int width, int height) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url)
                .apply(requestOptions
                        .override(width, height))
                .into(imageView);
    }

    public static void display(Context context, ImageView imageView, String url) {
        display(context, imageView, url, -1, -1);
    }

    public static void display(Context context, ImageView imageView, int resId) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(resId)
                .apply(requestOptions
                        .override(-1))
                .into(imageView);
    }

    public static void displaySmallPhoto(Context context, ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url)
                .apply(requestOptions
                        .override(-1))
                .thumbnail(0.5f)
                .into(imageView);
    }

    public static void displayBigPhoto(Context context, ImageView imageView, String url) {
        displayBigPhoto(context, imageView, url, null);
    }

    public static void displayBigPhoto(Context context, ImageView imageView, String url, RequestListener<Drawable> listener) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context)
                .load(url)
                .apply(requestOptions
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .override(Target.SIZE_ORIGINAL))
                .thumbnail(0.5f)
                .listener(listener)
                .into(imageView);
    }
}
