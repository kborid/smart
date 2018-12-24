package com.kborid.library.eventbus;


public interface ISubscriber {

    void onEvent(Event event);

    String[] getInterestedEvents();
}
