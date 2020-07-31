package com.kborid.java;

import com.kborid.java.dto.PersonInfo;

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
            e.printStackTrace();
            return "exception";
        } finally {
            return "finally";
        }
    }
}
