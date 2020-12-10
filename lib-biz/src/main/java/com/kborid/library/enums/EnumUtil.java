package com.kborid.library.enums;

import io.reactivex.annotations.NonNull;

/**
 * EnumUtil
 *
 * @description: 枚举工具类
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2020/8/14
 */
public final class EnumUtil {
    private EnumUtil() {
    }

    /**
     * 通过类型和code获取枚举值
     *
     * @param type      枚举类型
     * @param code      枚举代码code
     * @param enumClazz 枚举穷举类
     * @return 枚举值value
     */
    public static String getValueByTC(@NonNull String type, @NonNull String code, @NonNull Class<? extends IEnum> enumClazz) {
        for (IEnum e : enumClazz.getEnumConstants()) {
            if (type.equals(e.getType())) {
                if (code.equals(e.getCode())) {
                    return e.getValue();
                }
            }
        }
        return null;
    }
}
