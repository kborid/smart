package com.kborid.smart.test;

public class ThreadLocalTest {

    public static void main(String[] arg0) {
        final ThreadLocal<Object> mThreadLocal = new ThreadLocal<>();
        mThreadLocal.set(true);
        System.out.println("当前线程" + Thread.currentThread() + "ThreadLocal存储" + mThreadLocal.get());

        new Thread("thread1") {
            @Override
            public void run() {
                super.run();
                mThreadLocal.set(false);
                System.out.println("当前线程" + Thread.currentThread() + "ThreadLocal存储" + mThreadLocal.get());
            }
        }.start();

        new Thread("thread2") {
            @Override
            public void run() {
                super.run();
                mThreadLocal.set("123");
                System.out.println("当前线程" + Thread.currentThread() + "ThreadLocal存储" + mThreadLocal.get());
            }
        }.start();
    }

}
