package com.kborid.library.enums;

import io.reactivex.annotations.NonNull;

public final class EnumUtil {
    private EnumUtil() {
    }

    /**
     * 通过类型和code获取枚举值
     *
     * @param type
     * @param code
     * @param enumClazz
     * @return
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
