package com.kborid.smart.util;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kborid.smart.PRJApplication;
import com.kborid.smart.R;


/**
 * Toast img 统一管理类
 */
public class ToastDrawableUtil {

    private static Toast mToast;

    /**
     * 显示有icon的toast
     *
     * @param msg   消息
     * @param resId icon id
     */
    public static void showImgToast(final String msg, final int resId) {
        if (null != mToast) {
            mToast.cancel();
            mToast = null;
        }
        mToast = new Toast(PRJApplication.getInstance());

        View view = LayoutInflater.from(PRJApplication.getInstance()).inflate(R.layout.toast_custom, null);
        mToast.setView(view);

        ((TextView) view.findViewById(R.id.toast_custom_tv)).setText(msg);
        ImageView icon = (ImageView) view.findViewById(R.id.toast_custom_iv);
        if (resId > 0) {
            icon.setImageResource(resId);
        }

        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public static void showImgToast(final String msg) {
        showImgToast(msg, -1);
    }
}
