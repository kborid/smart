package com.kborid.setting.vm;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.kborid.setting.PRJApplication;

/**
 * ViewBindingAdapter.java
 *
 * @description:
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @date: 2021/2/26
 */
public class ViewBindingAdapter {

    private static RequestOptions mRequestOptions = null;

    private ViewBindingAdapter() {
    }

    @BindingAdapter("imgUrl")
    public static void setImgUrl(ImageView view, String url) {
        if (null == mRequestOptions) {
            mRequestOptions = new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
        }
        Glide.with(view)
//                .setDefaultRequestOptions(mRequestOptions)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target,
                            boolean isFirstResource) {
                        System.out.println("onLoadFailed()");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                            DataSource dataSource, boolean isFirstResource) {
                        System.out.println(String.format("onResourceReady()：DataSource=【%s】", dataSource.name()));
                        return false;
                    }
                })
                .into(view);
    }

    @BindingAdapter(value = {"bgColor", "fgColor"})
    public static void setTextColor(TextView view, int bgColor, int fgColor) {
        view.setTextColor(PRJApplication.getInstance().getColor(fgColor));
        view.setBackgroundColor(PRJApplication.getInstance().getColor(bgColor));
    }
}
