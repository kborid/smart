package com.kborid.setting.constant;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.kborid.setting.constant.CodeTypeConst.CODE_TYPE1;
import static com.kborid.setting.constant.CodeTypeConst.CODE_TYPE2;

/**
 * CodeType
 *
 * @description: CodeType
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @date: 2020/11/26
 */
@IntDef({CODE_TYPE1, CODE_TYPE2})
@Retention(RetentionPolicy.SOURCE)
public @interface CodeType {
}