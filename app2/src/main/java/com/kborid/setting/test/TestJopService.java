package com.kborid.setting.test;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;

import java.util.concurrent.Executors;

/**
 * TestJopService
 *
 * @description:
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2019/12/4
 */
public class TestJopService<T extends JobInfo> extends JobService {

    private JobParameters params;
    
    public T tt(T t) {
        System.out.println("\"tt\" = " + "tt");
        Executors.newCachedThreadPool();
        Executors.newFixedThreadPool(3);
        Executors.newSingleThreadExecutor();
        Executors.newScheduledThreadPool(3);
        return t;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        System.out.println("onStartJob()");
        this.params = params;
        AsyncTask.THREAD_POOL_EXECUTOR.execute(mRunnable);
        return true;
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            System.out.println("busy work start");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("busy work end");
            jobFinished(params, true);
        }
    };

    @Override
    public boolean onStopJob(JobParameters params) {
        System.out.println("onStopJob()");
        return false;
    }
}
