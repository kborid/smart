package com.kborid.setting.provider;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.os.IResultReceiver;

/**
 * SessionGetterImpl
 *
 * @description: demo
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 * @date: 2020/7/21
 */
public class SessionGetterImpl extends IResultReceiver.Stub {

    public SessionGetterImpl(Context context) {
    }

    @Override
    public void send(int i, Bundle bundle) throws RemoteException {

    }
}
