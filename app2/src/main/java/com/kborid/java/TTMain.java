package com.kborid.java;

import com.kborid.java.dto.PersonInfo;
import com.kborid.library.util.LogUtils;

import java.lang.reflect.Constructor;

public class TTMain {
    public static void main(String[] args) {
//        System.out.println(tryCatch());

        PersonInfo personInfo = new PersonInfo("段伟", null);
//        System.out.println(personInfo.getAge());
//        Integer integer = null;
//        int t = integer;

        String str = "我在方法外面";
        funStringTest(str);
        System.out.println(str);
        Integer in = 1;
        funIntTest(in);
        System.out.println(in);
        funObjectTest(personInfo);
        System.out.println(personInfo.toString());

        try {
            Class<?> clazz = Class.forName("com.kborid.java.dto.PersonInfo");
            try {
//                PersonInfo personInfo1 = (PersonInfo) clazz.newInstance();
                Constructor<?> con = clazz.getDeclaredConstructor(String.class, Integer.class);
                PersonInfo personInfo1 = (PersonInfo) con.newInstance(new Object[]{"dd", 3});
                System.out.println(personInfo1.toString());
            } catch (Exception e) {
                LogUtils.e(e);
            }
        } catch (ClassNotFoundException e) {
            LogUtils.e(e);
        }
    }

    private static void funStringTest(String str) {
        str = "我在方法里面";
    }

    private static void funIntTest(Integer in) {
        in = 100;
    }

    private static void funObjectTest(PersonInfo info) {
        info.setAge(100);
    }

    private static String tryCatch() {
        String ret = "init";
        String s = null;
        try {
            s.length();
            return "normal";
        } catch (Exception e) {
            LogUtils.e(e);
            return "exception";
        } finally {
            return "finally";
        }
    }
}
