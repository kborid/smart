package com.kborid.java;

public class ThreadExceptionDemo {
    public static void main(String[] args) {
        Worker worker = new Worker("tt-thread");
        worker.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println("uncaughtException");
            }
        });
        worker.start();
    }

    static class Worker extends Thread {
        Worker(String name) {
            super(name);
        }

        @Override
        public void run() {
            super.run();
            System.out.println("run");
            String s = null;
            try {
                s.length();
            } catch (Exception e) {
                System.out.println("e");
            }
        }
    }
}
