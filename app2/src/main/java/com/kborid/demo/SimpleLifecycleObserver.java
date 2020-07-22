package com.kborid.demo;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleLifecycleObserver implements LifecycleObserver {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String actName;

    public SimpleLifecycleObserver(String actName) {
        this.actName = actName;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void create() {
        logger.info("执行{}的Create生命周期方法", actName);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        logger.info("执行{}的Start生命周期方法", actName);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void resume() {
        logger.info("执行{}的Resume生命周期方法", actName);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void pause() {
        logger.info("执行{}的Pause生命周期方法", actName);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {
        logger.info("执行{}的Stop生命周期方法", actName);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void destroy() {
        logger.info("执行{}的Destroy生命周期方法", actName);
    }
}
