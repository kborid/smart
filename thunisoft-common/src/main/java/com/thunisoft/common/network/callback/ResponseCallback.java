package com.thunisoft.common.network.callback;

public interface ResponseCallback<T> {
    void failure(Throwable e);

    void success(T data);
}
