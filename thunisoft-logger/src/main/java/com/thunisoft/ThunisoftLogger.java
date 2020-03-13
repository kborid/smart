package com.thunisoft;

import android.app.Application;
import android.content.Context;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.DiskLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.thunisoft.logger.DiskFormatStrategy;
import com.thunisoft.logger.LoggerConfig;

public class ThunisoftLogger {

    /**
     * logger初始化封装
     *
     * @param context
     * @param config
     */
    public static void initLogger(Context context, final LoggerConfig config) {

        if (!(context instanceof Application)) {
            throw new IllegalArgumentException("must be use application context init it");
        }

        // logger打印策略
        FormatStrategy mFormatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)   // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(0)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag(config.getTag())
                .build();

        // android log设置
        Logger.addLogAdapter(new AndroidLogAdapter(mFormatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return config.isDebug();
            }
        });

        // logger存储文件格式策略
        Logger.addLogAdapter(new DiskLogAdapter(DiskFormatStrategy.newBuilder().build(context)));
    }
}
