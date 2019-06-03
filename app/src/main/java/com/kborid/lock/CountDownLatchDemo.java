package com.kborid.lock;

import com.kborid.library.util.LogUtils;
import com.kborid.smart.util.DateUtils;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);//两个工人的协作
        Worker worker1 = new Worker("zhang san", 5000, latch);
        Worker worker2 = new Worker("li si", 8000, latch);
        worker1.start();//
        worker2.start();//
        latch.await();//等待所有工人完成工作
        LogUtils.d("all work done at " + DateUtils.getCurrentFormatDateString());
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
            LogUtils.d("Worker " + workerName + " do work begin at " + DateUtils.getCurrentFormatDateString());
            doWork();//工作了
            LogUtils.d("Worker " + workerName + " do work complete at " + DateUtils.getCurrentFormatDateString());
            latch.countDown();//工人完成工作，计数器减一
        }

        private void doWork() {
            try {
                int times = workTime / 1000;
                for (int i = 0; i < times; i++) {
                    Thread.sleep(1000);
                    LogUtils.d(workerName + " doing work---");
                }
            } catch (InterruptedException e) {
                LogUtils.e(e);
            }
        }
    }
}