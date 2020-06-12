package com.kborid.setting.demo;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleLifecycleObserver implements LifecycleObserver {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public SimpleLifecycleObserver(Logger logger) {
        if (null != logger) {
            this.logger = logger;
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void resume() {
        logger.info("执行Resume生命周期方法");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void pause() {
        logger.info("执行Pause生命周期方法");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void destroy() {
        logger.info("执行Destroy生命周期方法");
    }
}
