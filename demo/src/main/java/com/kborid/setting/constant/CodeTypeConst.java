package com.kborid.setting.constant;

public interface CodeTypeConst {
    /**
     * code test
     */
    int CODE_TYPE1 = 0;
    int CODE_TYPE2 = 1;

    /**
     * life cycle
     */
    int UNDEFINED = -1;
    int PRE_ON_CREATE = 0;
    int ON_CREATE = 1;
    int ON_START = 2;
    int ON_RESUME = 3;
    int ON_PAUSE = 4;
    int ON_STOP = 5;
    int ON_DESTROY = 6;
    int ON_RESTART = 7;
}
