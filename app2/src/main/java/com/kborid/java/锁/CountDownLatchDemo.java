package com.kborid.java.锁;

import com.kborid.library.util.DateTestUtils;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatchDemo
 *
 * @description: 减数器
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @date: 2020/8/4
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);//两个工人的协作
        Worker worker1 = new Worker("张三", 5000, 1.5f, latch);
        Worker worker2 = new Worker("李四", 10000, 0.8f, latch);
        worker1.start();
        worker2.start();
        latch.await(); //等待所有工人完成工作
        System.out.println("all work done at " + DateTestUtils.getCurrentFormatDateString());
    }


    /**
     * 工人
     */
    static class Worker extends Thread {
        String workerName;
        int workTime;
        float workSpeed;
        CountDownLatch latch;

        public Worker(String workerName, int workTime, float workSpeed, CountDownLatch latch) {
            this.workerName = workerName;
            this.workTime = workTime;
            this.workSpeed = workSpeed;
            this.latch = latch;
        }

        @Override
        public void run() {
            System.out.println("Worker " + workerName + " do work begin at " + DateTestUtils.getCurrentFormatDateString());
            int wordSpendTime = (int) (1000 * workSpeed);
            doWork(wordSpendTime);//工作了
            System.out.println("Worker " + workerName + " do work complete at " + DateTestUtils.getCurrentFormatDateString());
            latch.countDown();//工人完成工作，计数器减一
        }

        private void doWork(int workSpeedTime) {
            try {
                int times = workTime / workSpeedTime;
                for (int i = 0; i < times; i++) {
                    Thread.sleep(workSpeedTime);
                    System.out.println(workerName + " doing work---count: " + i);
                }
            } catch (InterruptedException e) {
                System.out.println("InterruptedException");
            }
        }
    }
}