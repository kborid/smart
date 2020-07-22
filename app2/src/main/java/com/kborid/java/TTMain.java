package com.kborid.java;

import com.kborid.java.dto.PersonInfo;

public class TTMain {
    public static void main(String[] args) {
//        System.out.println(tryCatch());

        PersonInfo personInfo = new PersonInfo("段伟", null);
        System.out.println(personInfo.getAge());
        Integer integer = null;
        int t = integer;
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
