package com.kborid.smart.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kborid.library.util.PackageUtils;
import com.kborid.smart.R;

import androidx.annotation.Nullable;

public class ThirdLayout extends LinearLayout {
    private static final String WX_PKG = "com.tencent.mm";
    private static final String WX_CLASS = "com.tencent.mm.ui.LauncherUI";
    private static final String QQ_PKG = "com.tencent.mobileqq";
    private static final String QQ_CLASS = "com.tencent.mobileqq.activity.SplashActivity";
    private static final String ALIPAY_PKG = "com.eg.android.AlipayGphone";
    private static final String ALIPAY_CLASS = "com.eg.android.AlipayGphone.AlipayLogin";

    private static final ArrayMap<String, String> packageName;

    static {
        packageName = new ArrayMap<>();
        packageName.put(WX_PKG, WX_CLASS);
        packageName.put(QQ_PKG, QQ_CLASS);
        packageName.put(ALIPAY_PKG, ALIPAY_CLASS);
    }

    private Context mContext;

    public ThirdLayout(Context context) {
        this(context, null);
    }

    public ThirdLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThirdLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_third, this);
        LinearLayout third = (LinearLayout) findViewById(R.id.third);
        packageName.keySet().stream()
                .filter(PackageUtils::isInstalled)
                .forEach(key -> {
                    try {
                        ImageView icon = new ImageView(mContext);
                        LayoutParams llp = new LayoutParams(100, 100);
                        llp.setMargins(10, 10, 10, 10);
                        third.addView(icon, llp);
                        icon.setImageDrawable(mContext.getPackageManager().getApplicationIcon(key));
                        icon.setOnClickListener(v -> {
                            System.out.println(packageName.get(key));
                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName(key, packageName.get(key)));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        });
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                });
        System.out.println(third.getChildCount());
    }
}
