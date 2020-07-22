package com.kborid.setting;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.IntDef;

import static com.kborid.setting.CodeTypeConst.CODE_TYPE1;
import static com.kborid.setting.CodeTypeConst.CODE_TYPE2;

@IntDef({CODE_TYPE1, CODE_TYPE2})
@Retention(RetentionPolicy.SOURCE)
public @interface CodeType {
}