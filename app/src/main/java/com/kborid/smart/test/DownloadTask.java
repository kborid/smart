package com.kborid.smart.test;

import com.kborid.library.util.LogUtils;

public class DownloadTask implements IDownloadTask, Runnable {

    private int id;
    private String url;
    private IDownloadTask task;

    public DownloadTask(int id, String url, IDownloadTask task) {
        this.id = id;
        this.url = url;
        this.task = task;
    }

    @Override
    public void notifyStart() {

    }

    @Override
    public void notifyProgress(int progress) {

    }

    @Override
    public void notifyFinish() {

    }

    @Override
    public void run() {
        if (null != task) {
            task.notifyStart();
        }

        LogUtils.d(Thread.currentThread().getName(), "thread print i = " + id);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
