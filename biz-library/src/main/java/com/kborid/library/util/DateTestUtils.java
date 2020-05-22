package com.kborid.library.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTestUtils {

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
