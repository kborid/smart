package com.kborid.library.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     * 格式化日期
     *
     * @param pattern
     * @param date
     * @return
     */
    public static String formatDate(String pattern, Date date) {
        return new SimpleDateFormat(pattern).format(date);
    }
}
