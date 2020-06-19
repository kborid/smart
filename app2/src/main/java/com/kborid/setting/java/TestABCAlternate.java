package com.kborid.setting.java;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestABCAlternate{
    public static void main(String[] args){
        AlternateDemo ad = new AlternateDemo();

        new Thread(new Runnable(){
            public void run(){
                for(int i=1; i<=5; i++){
                    ad.loopA(i);
                }
            }
        },"A").start();

        new Thread(new Runnable(){
            public void run(){
                for(int i=1; i<=5; i++){
                    ad.loopB(i);
                }
            }
        },"B").start();

        new Thread(new Runnable(){
            public void run(){
                for(int i=1; i<=5; i++){
                    ad.loopC(i);

                    System.out.println("--------------------");
                }
            }
        },"C").start();
    }
}

class AlternateDemo{

    private int number = 1; // 当前正在执行线程的标记

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    // totalLoop 表示循环第几轮
    // 线程A
    public void loopA(int totalLoop){
        // 上锁
        lock.lock();

        try{
            // 1. 判断
            if(number != 1){
                condition1.await();
            }

            // 2. 打印
            for(int i=1; i <= 1; i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
            }

            // 3. 唤醒线程B
            number = 2;
            condition2.signal();

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            // 释放锁
            lock.unlock();
        }
    }

    // 线程B
    public void loopB(int totalLoop){
        // 上锁
        lock.lock();

        try{
            // 1. 判断
            if(number != 2){
                condition2.await();
            }

            // 2. 打印
            for(int i=1; i <= 1; i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
            }

            // 3. 唤醒线程C
            number = 3;
            condition3.signal();

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            // 释放锁
            lock.unlock();
        }
    }

    // 线程C
    public void loopC(int totalLoop){
        // 上锁
        lock.lock();

        try{
            // 1. 判断
            if(number != 3){
                condition3.await();
            }

            // 2. 打印
            for(int i=1; i <= 1; i++){
                System.out.println(Thread.currentThread().getName()+"\t"+i+"\t"+totalLoop);
            }

            // 3. 唤醒线程A
            number = 1;
            condition1.signal();

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            // 释放锁
            lock.unlock();
        }
    }
}