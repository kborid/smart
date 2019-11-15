package com.kborid.smart.lock;

import com.kborid.library.util.DateTestUtils;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);//两个工人的协作
        Worker worker1 = new Worker("张三", 5000, latch);
        Worker worker2 = new Worker("李四", 8000, latch);
        worker1.start();
        worker2.start();
        latch.await(); //等待所有工人完成工作
        System.out.println("all work done at " + DateTestUtils.getCurrentFormatDateString());
    }


    static class Worker extends Thread {
        String workerName;
        int workTime;
        CountDownLatch latch;

        public Worker(String workerName, int workTime, CountDownLatch latch) {
            this.workerName = workerName;
            this.workTime = workTime;
            this.latch = latch;
        }

        @Override
        public void run() {
            System.out.println("Worker " + workerName + " do work begin at " + DateTestUtils.getCurrentFormatDateString());
            doWork();//工作了
            System.out.println("Worker " + workerName + " do work complete at " + DateTestUtils.getCurrentFormatDateString());
            latch.countDown();//工人完成工作，计数器减一
        }

        private void doWork() {
            try {
                int times = workTime / 1000;
                for (int i = 0; i < times; i++) {
                    Thread.sleep(1000);
                    System.out.println(workerName + " doing work---");
                }
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }
        }
    }
}