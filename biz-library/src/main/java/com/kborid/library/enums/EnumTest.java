package com.kborid.library.enums;

/**
 * EnumTest
 *
 * @description: 枚举穷举类
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2020/8/14
 */
public enum EnumTest implements IEnum {

    // 科目
    SUBJECT1(EnumConst.TYPE_SUBJECT, "1", "科目1"),
    SUBJECT2(EnumConst.TYPE_SUBJECT, "2", "科目2"),
    SUBJECT3(EnumConst.TYPE_SUBJECT, "3", "科目3"),
    SUBJECT4(EnumConst.TYPE_SUBJECT, "4", "科目3"),

    // 测试
    TYPE1(EnumConst.TYPE_TEST, "1", "类型1"),
    TYPE2(EnumConst.TYPE_TEST, "2", "类型2"),
    TYPE3(EnumConst.TYPE_TEST, "3", "类型3"),
    TYPE4(EnumConst.TYPE_TEST, "4", "类型4");

    private String type;
    private String code;
    private String value;

    EnumTest(String type, String code, String value) {
        this.type = type;
        this.code = code;
        this.value = value;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static void main(String[] args) {
        System.out.println(EnumUtil.getValueByTC(EnumConst.TYPE_TEST, "3", EnumTest.class));
        System.out.println(EnumUtil.getValueByTC(EnumConst.TYPE_SUBJECT, "4", EnumTest.class));
    }
}
