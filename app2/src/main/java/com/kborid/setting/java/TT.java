package com.kborid.setting.java;

public class TT {
    public static void main(String[] args) {
        System.out.println(tryCatch());
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
