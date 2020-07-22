package com.kborid.java.tt;

public class ThreadLocalTest {

    public static void main(String[] arg0) {
        final ThreadLocal<String> mStringThreadLocal = new ThreadLocal<>();
        final ThreadLocal<Integer> mIntegerThreadLocal = new ThreadLocal<>();
        mStringThreadLocal.set("init");
        System.out.println("当前线程" + Thread.currentThread() + "ThreadLocal存储" + mStringThreadLocal.get());

        new Thread("thread1") {
            @Override
            public void run() {
                super.run();
                mStringThreadLocal.set("我是线程1");
                mIntegerThreadLocal.set(2);
                System.out.println("当前线程" + Thread.currentThread() + "ThreadLocal存储" + mStringThreadLocal.get());
                System.out.println("当前线程" + Thread.currentThread() + "ThreadLocal存储" + mIntegerThreadLocal.get());
            }
        }.start();

        new Thread("thread2") {
            @Override
            public void run() {
                super.run();
                mStringThreadLocal.set("我是线程2");
                mIntegerThreadLocal.set(20);
                System.out.println("当前线程" + Thread.currentThread() + "ThreadLocal存储" + mStringThreadLocal.get());
                System.out.println("当前线程" + Thread.currentThread() + "ThreadLocal存储" + mIntegerThreadLocal.get());
            }
        }.start();
    }
}
