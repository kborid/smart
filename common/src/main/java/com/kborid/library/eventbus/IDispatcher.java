package com.kborid.library.eventbus;


public interface IDispatcher extends IPublisher {

    void subscribe(Object subscriber);

    void unsubscribe(Object subscriber);

}

