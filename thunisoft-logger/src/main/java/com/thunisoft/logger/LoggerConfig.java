package com.thunisoft.logger;

public class LoggerConfig {
    //默认TAG
    private static final String TAG_DEFAULT = "TAG_DEFAULT";

    /**
     * 设置logger的打印Tag
     *
     * @return
     */
    public String getTag() {
        return TAG_DEFAULT;
    }

    /**
     * 设置debug or release
     *
     * @return
     */
    public boolean isDebug() {
        return false;
    }
}
