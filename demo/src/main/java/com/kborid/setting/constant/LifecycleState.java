package com.kborid.setting.constant;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.kborid.setting.constant.CodeTypeConst.*;

/**
 * LifecycleState
 *
 * @description: LifecycleState
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @date: 2020/11/26
 */
@IntDef(value = {
        UNDEFINED,
        PRE_ON_CREATE,
        ON_CREATE,
        ON_START,
        ON_RESUME,
        ON_PAUSE,
        ON_STOP,
        ON_DESTROY,
        ON_RESTART
})
@Retention(RetentionPolicy.SOURCE)
public @interface LifecycleState {
}
