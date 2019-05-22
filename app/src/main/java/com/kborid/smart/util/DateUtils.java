package com.kborid.smart.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static final ThreadLocal<DateFormat> sdf = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    public static String getCurrentFormatDateString() {
        return sdf.get().format(new Date());
    }
}
