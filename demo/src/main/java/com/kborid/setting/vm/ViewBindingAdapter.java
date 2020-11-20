package com.kborid.setting.vm;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.kborid.setting.PRJApplication;

public class ViewBindingAdapter {
    @BindingAdapter("imgUrl")
    public static void setImgUrl(ImageView view, String url) {
        Glide.with(view).load(url).into(view);
    }

    @BindingAdapter(value = {"bgColor", "fgColor"})
    public static void setTextColor(TextView view, int bgColor, int fgColor) {
        view.setTextColor(PRJApplication.getInstance().getColor(fgColor));
        view.setBackgroundColor(PRJApplication.getInstance().getColor(bgColor));
    }
}
