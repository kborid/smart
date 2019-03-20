package com.kborid.smart.test;

public interface IDownloadTask {
    void notifyStart();
    void notifyProgress(int progress);
    void notifyFinish();
}
