package com.kborid.demo.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

import com.kborid.setting.PRJApplication;

public class TestMessengerClient {

    private static Messenger clientMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            System.out.println("From Service");
            String mm = (String) msg.obj;
            System.out.println(mm);
        }
    });

    private static Messenger serviceMessenger;

    public static void init() {
        Intent intent = new Intent(PRJApplication.getInstance(), TestMessengerServer.class);
        PRJApplication.getInstance().bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                if (null == serviceMessenger) {
                    serviceMessenger = new Messenger(service);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                serviceMessenger = null;
            }
        }, Context.BIND_AUTO_CREATE);
    }

    public static void send(String msg) {
        if (null == serviceMessenger) {
            return;
        }

        Message message = Message.obtain();
        message.obj = msg;
        message.replyTo = clientMessenger;
        try {
            serviceMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
