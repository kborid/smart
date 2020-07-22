package com.kborid.java.异常;

public class ThrowableDemo {
    private static void throwException(String msg) throws Exception {
        throw new Exception(msg);
    }

    private static void throwRuntimeException(String msg) {
        throw new RuntimeException(msg);
    }

    public static void main(String[] args) {
        try {
            throwException("抛出普通异常");
        } catch (Exception e) {
            e.printStackTrace();
        }
        throwRuntimeException("抛出运行时异常");
    }
}
